/*
Copyright 2024 Kyrylov Ivan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package io.github.kivanval.gradle;

import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.tasks.DefaultSourceSetOutput;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.scala.ScalaBasePlugin;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.internal.Cast;
import org.gradle.util.internal.GUtil;

public class AvrohuggerBasePlugin implements Plugin<Project> {
	@Getter(AccessLevel.NONE)
	private final ObjectFactory objects;

	@Inject
	public AvrohuggerBasePlugin(ObjectFactory objects) {
		this.objects = objects;
	}

	@Override
	public void apply(Project project) {
		project.getPluginManager().apply(ScalaBasePlugin.class);

		configureSourceSetDefaults(project, objects);
		configureExtension(project);
	}

	private static void configureSourceSetDefaults(final Project project, final ObjectFactory objects) {
		final var sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
		sourceSets.all(sourceSet -> {
			final var displayName = GUtil.toWords(sourceSet.getName()) + " Avro source";
			final var avro = objects.sourceDirectorySet("avro", displayName);
			sourceSet.getExtensions().add(SourceDirectorySet.class, "avro", avro);
			avro.srcDir("src/" + sourceSet.getName() + "/" + avro.getName());

			sourceSet.getAllSource().source(avro);
			sourceSet.getResources().source(avro);

			// TODO Maybe, move in task creating step
			final var output = Cast.cast(DefaultSourceSetOutput.class, sourceSet.getOutput());
			final var avroScalaGeneratedPath = "generated/sources/avrohugger/scala/" + sourceSet.getName();
			output.getGeneratedSourcesDirs().from(project.getLayout().getBuildDirectory().dir(avroScalaGeneratedPath));
		});
	}

	private static final String AVROHUGGER_EXTENSION_NAME = "avrohugger";

	private static void configureExtension(final Project project) {
		project.getExtensions().create(AvrohuggerExtension.class, AVROHUGGER_EXTENSION_NAME,
				DefaultAvrohuggerExtension.class);
	}
}

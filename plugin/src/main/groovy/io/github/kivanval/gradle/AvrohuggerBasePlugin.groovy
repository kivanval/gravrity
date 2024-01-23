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
package io.github.kivanval.gradle

import groovy.transform.CompileStatic
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.tasks.DefaultSourceSetOutput
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.scala.ScalaBasePlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.internal.Cast
import org.gradle.util.internal.GUtil

@CompileStatic
class AvrohuggerBasePlugin implements Plugin<Project> {
	private final ObjectFactory objects

	@Inject
	AvrohuggerBasePlugin(ObjectFactory objects) {
		this.objects = objects
	}

	@Override
	void apply(Project project) {
		project.pluginManager.apply(ScalaBasePlugin)

		configureSourceSetDefaults(project, objects)
		configureExtension(project)
	}

	private static void configureSourceSetDefaults(final Project project, final ObjectFactory objects) {
		final def sourceSets = project.extensions.getByType(SourceSetContainer)
		sourceSets.configureEach { SourceSet sourceSet ->
			final def displayName = GUtil.toWords(sourceSet.name) + " Avro source"
			final def avro = objects.sourceDirectorySet("avro", displayName)
			sourceSet.getExtensions().add(SourceDirectorySet, "avro", avro)
			avro.srcDir("src/" + sourceSet.name + "/" + avro.name)

			sourceSet.allSource.source(avro)
			sourceSet.resources.source(avro)

			// TODO Maybe, move in task creating step
			final def output = Cast.cast(DefaultSourceSetOutput, sourceSet.output)
			final def avroScalaGeneratedPath = "generated/sources/avrohugger/scala/" + sourceSet.name
			output.generatedSourcesDirs.from(project.layout.buildDirectory.dir(avroScalaGeneratedPath))
		}
	}

	private static final String AVROHUGGER_EXTENSION_NAME = "avrohugger"

	private static void configureExtension(final Project project) {
		project.extensions.create(AvrohuggerExtension, AVROHUGGER_EXTENSION_NAME,
				DefaultAvrohuggerExtension)
	}
}

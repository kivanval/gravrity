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

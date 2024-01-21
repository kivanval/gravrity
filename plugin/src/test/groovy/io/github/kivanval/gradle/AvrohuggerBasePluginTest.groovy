package io.github.kivanval.gradle

import groovy.transform.CompileDynamic
import java.nio.file.Paths
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

@CompileDynamic
class AvrohuggerBasePluginTest extends Specification {
	def "plugin should have avro default settings in sourceSets using ScalaPlugin"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.with {
			apply(ScalaPlugin)
			apply(AvrohuggerPlugin)
		}

		then:
		def sourceSet = project.sourceSets.getByName(sourceSetName)
		sourceSet.avro.srcDirs.collect { it.toString() } == [
			Paths.get(project.projectDir.toString(), "src", sourceSetName, "avro").toString()
		]


		sourceSet.output.generatedSourcesDirs.collect {it.toString() }.contains(
		Paths.get(project.layout.buildDirectory.asFile.get().toString(), "generated", "sources", "avrohugger", "scala", sourceSetName).toString()
		)

		where:
		sourceSetName << ['main', 'test']
	}
}

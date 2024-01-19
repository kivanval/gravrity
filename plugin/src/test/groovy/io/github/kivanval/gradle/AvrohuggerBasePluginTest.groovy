package io.github.kivanval.gradle

import groovy.transform.CompileDynamic
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

@CompileDynamic
class AvrohuggerBasePluginTest extends Specification {
	@Unroll
	def "plugin should have extension with #sourceSet sourceSets"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.apply(AvrohuggerPlugin)

		then:
		project.avrohugger.sourceSets.getByName(sourceSetName) != null

		where:
		sourceSetName << ['main', 'test']
	}

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
		sourceSet.avro.srcDirs.collect { "$it" } == [
			"${project.projectDir}/src/$sourceSetName/avro"
		]
		sourceSet.output.generatedSourcesDirs.collect { "$it" }.contains(
		"${project.layout.buildDirectory.asFile.get()}/generated/sources/avrohugger/scala/$sourceSetName"
		)

		where:
		sourceSetName << ['main', 'test']
	}
}

package io.github.kivanval.gradle

import groovy.transform.CompileDynamic
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

@CompileDynamic
class AvrohuggerBasePluginTest extends Specification {
	def "plugin has avro directories by default using ScalaPlugin"() {
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
			"${project.projectDir}/src/$sourceSetName/avro"
		]
		sourceSet.output.generatedSourcesDirs.collect { it.toString() }.contains(
		"${project.layout.buildDirectory}/generated/sources/avrohugger/scala/$sourceSetName"
		)

		where:
		sourceSetName << ['main', 'test']
	}

	@Unroll
	def "plugin has extension with #sourceSet sourceSets"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.with {
			apply(ScalaPlugin)
			apply(AvrohuggerPlugin)
		}

		then:
		project.avrohugger.sourceSets.getByName(sourceSet) != null

		where:
		sourceSet << ['main', 'test']
	}
}

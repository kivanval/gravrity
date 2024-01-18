package io.github.kivanval.gradle

import groovy.transform.CompileDynamic
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

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
		project.sourceSets.getByName(sourceSet).avro.srcDirs.collect{ it.toString() } == [
			"${project.projectDir}/src/$sourceSet/avro"
		]

		where:
		sourceSet << ['main', 'test']
	}
}

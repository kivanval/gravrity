package io.sofa.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class AvrohuggerPluginTest extends Specification {
	def "plugin applies base plugin"() {
		given:
		Project project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.apply(AvrohuggerPlugin)

		then:
		project.plugins.hasPlugin(AvrohuggerBasePlugin)
	}

	def "plugin resisters generateAvroScala task"() {
		given:
		Project project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.apply(AvrohuggerPlugin)

		then:
		project.tasks.named('generateAvroScala').present
	}
}

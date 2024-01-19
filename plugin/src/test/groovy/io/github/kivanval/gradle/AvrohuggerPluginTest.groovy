package io.github.kivanval.gradle


import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.Specification

class AvrohuggerPluginTest extends Specification {
	def "plugin applies base plugin"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.apply(AvrohuggerPlugin)

		then:
		project.plugins.hasPlugin(AvrohuggerBasePlugin)
	}

	@Ignore
	def "plugin registers generateAvroScala task"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.apply(AvrohuggerPlugin)

		then:
		project.tasks.named('generateAvroScala').present
	}
}

package io.github.kivanval.gradle

import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class AvrohuggerBasePluginTest extends Specification {
	def "plugin always has extension with type SourceSetContainer"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.with {
			pluginManager.with {
				apply(JavaBasePlugin)
				apply(AvrohuggerPlugin)
			}
			extensions.getByType(SourceSetContainer)
		}

		then:
		notThrown(UnknownDomainObjectException)
	}
}

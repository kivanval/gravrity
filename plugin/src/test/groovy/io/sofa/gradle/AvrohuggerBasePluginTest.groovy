package io.sofa.gradle

import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class AvrohuggerBasePluginTest extends Specification {
	def "plugin always has extension with type SourceSetContainer"() {
		given:
		Project project = ProjectBuilder.builder().build()

		when:
		project.with {
			pluginManager.with {
				apply(AvrohuggerPlugin)
				apply(JavaBasePlugin)
			}
			extensions.getByType(SourceSetContainer)
		}

		then:
		notThrown(UnknownDomainObjectException)
	}
}

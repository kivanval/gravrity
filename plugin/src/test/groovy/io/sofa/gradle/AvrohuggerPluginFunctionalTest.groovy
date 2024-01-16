package io.sofa.gradle

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir


class AvrohuggerPluginFunctionalTest extends Specification {
	@Shared @TempDir File projectDir
	@Shared File buildFile
	@Shared File settingsFile

	def setupSpec() {
		buildFile = new File(projectDir, "build.gradle")
		buildFile << """
           plugins {
               id('java')
               id('io.sofa.avrohugger')
           }
           sourceSets {
               main {
                   avro {
                   	 srcDir ''
                   }
               }
           }
           """
		settingsFile = new File(projectDir, "settings.gradle")
		settingsFile << ""
	}

	def "can run task"() {
		when:
		GradleRunner.create()
				.forwardOutput()
				.withPluginClasspath()
				.withArguments("compileJava")
				.withProjectDir(projectDir)
				.build()

		then:
		noExceptionThrown()
	}
}

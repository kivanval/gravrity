/*
Copyright 2024 Kyrylov Ivan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package io.github.kivanval.gradle

import io.github.kivanval.gradle.util.DependencyUtils
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class DependencyUtilsTest extends Specification {
	def "FindScalaVersion"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.with {
			apply(ScalaPlugin)
		}
		project.dependencies.with {
			add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, scalaLibrary("2.13.11"))
		}

		then:
		DependencyUtils.findScalaVersion(project)
	}

	private static String scalaLibrary(String version) {
		"${DependencyUtils.SCALA_GROUP}:${DependencyUtils.SCALA_ARTIFACT}:$version"
	}
}

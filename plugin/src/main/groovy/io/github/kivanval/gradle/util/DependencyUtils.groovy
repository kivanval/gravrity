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
package io.github.kivanval.gradle.util


import groovy.transform.CompileStatic
import javax.annotation.Nullable
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

@CompileStatic
class DependencyUtils {
	static final String SCALA_GROUP = 'org.scala-lang'
	static final String SCALA_ARTIFACT = 'scala-library'

	@Nullable
	static String findScalaVersion(Project project) {
		findCompileDependencyVersion SCALA_GROUP, SCALA_ARTIFACT, project
	}


	@Nullable
	static String findCompileDependencyVersion(String group, String artifact, Project project) {
		project
				.configurations
				.named(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME)
				.map { it.allDependencies }
				.getOrElse(null)
				?.find { group == it.group && artifact == it.name }
				?.version
	}
}
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
	static final String SCALA2_ARTIFACT = 'scala-library'
	static final String SCALA3_ARTIFACT = 'scala3-library_3'

	@Nullable
	static String findScalaVersion(final Project project) {
		findCompileDependencyVersion(SCALA_GROUP, SCALA2_ARTIFACT, project) ?:
				findCompileDependencyVersion(SCALA_GROUP, SCALA3_ARTIFACT, project) ?:
				project.property('default.scala.version')
	}


	@Nullable
	static String findCompileDependencyVersion(final String group, final String artifact, final Project project) {
		project
				.configurations
				.named(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME)
				.map { it.allDependencies }
				.getOrNull()
				?.find { group == it.group && artifact == it.name }
				?.version
	}
}

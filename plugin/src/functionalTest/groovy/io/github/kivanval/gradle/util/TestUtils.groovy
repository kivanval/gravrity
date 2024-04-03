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

import org.gradle.testkit.runner.GradleRunner

class TestUtils {

  static final List<String> GRADLE_VERSIONS = ["7.2", "7.3", "7.4.2", "8.2", "8.5"]

  static GradleRunner gradleRunner(
    File projectDir,
    String gradleVersion,
    String... arguments
  ) {
    GradleRunner.create()
      .forwardOutput()
      .withPluginClasspath()
      .withArguments(arguments)
      .withProjectDir(projectDir)
      .withGradleVersion(gradleVersion)
  }
}

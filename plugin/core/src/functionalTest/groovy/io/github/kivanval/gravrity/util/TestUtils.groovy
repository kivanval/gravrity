/*
Copyright 2024 Ivan Kyrylov

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
package io.github.kivanval.gravrity.util

import groovy.text.SimpleTemplateEngine
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.gradle.internal.impldep.com.google.common.io.Resources
import org.gradle.testkit.runner.GradleRunner

class TestUtils {

  static final List<String> GRADLE_VERSIONS = [
    "8.0",
    "8.2",
    "8.4",
    "8.8"
  ]

  static GradleRunner gradleRunner(
    Path projectDir,
    String gradleVersion,
    String... arguments
  ) {
    GradleRunner.create()
      .forwardOutput()
      .withPluginClasspath()
      .withArguments(arguments.toList() << "--info")
      .withEnvironment(System.getenv()) // Enable forking
      .forwardStdOutput(new OutputStreamWriter(System.out))
      .forwardStdError(new OutputStreamWriter(System.err))
      .withProjectDir(projectDir.toFile())
      .withGradleVersion(gradleVersion)
  }

  private static final engine = new SimpleTemplateEngine()

  static String resource(Map binding, String resourceName) {
    def template = new File(getResourceURI(resourceName))
    engine.createTemplate(template).make(binding).toString()
  }

  static String resource(String resourceName) {
    Files.readString(Paths.get(getResourceURI(resourceName)))
  }

  static private getResourceURI(String resourceName) {
    Resources.getResource(resourceName).toURI()
  }
}

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

import groovy.text.SimpleTemplateEngine
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.gradle.internal.impldep.com.google.common.io.Resources
import org.gradle.testkit.runner.GradleRunner

class TestUtils {

  static final List<String> GRADLE_VERSIONS = [
    "7.2",
    "7.3",
    "7.4.2",
    "8.2",
    "8.5"
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
    namespace(binding)
    engine.createTemplate(template).make(binding).toString()
  }

  static String resource(String resourceName) {
    Files.readString(Paths.get(getResourceURI(resourceName)))
  }

  static private getResourceURI(String resourceName) {
    Resources.getResource(resourceName).toURI()
  }

  static private namespace(Map binding) {
    if (binding.containsKey('namespace')) {
      binding.put('namespace', "\"namespace\":\"${binding.get('namespace').replace('/','.')}\",")
    } else {
      binding.put('namespace', '')
    }
  }
}

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

import io.github.kivanval.ResourceHelper
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir


class AvrohuggerPluginFunctionalTest extends Specification {
  @Shared
  @TempDir
  File projectDir
  @Shared
  File buildFile
  @Shared
  File settingsFile

  def setupSpec() {
    buildFile = new File(projectDir, "build.gradle")
    buildFile << ResourceHelper.read("sample.gradle")
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

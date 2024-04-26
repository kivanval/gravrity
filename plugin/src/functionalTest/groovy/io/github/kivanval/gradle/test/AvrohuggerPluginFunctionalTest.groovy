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
package io.github.kivanval.gradle.test

import io.github.kivanval.gradle.util.ResourceUtils
import io.github.kivanval.gradle.util.TestUtils
import java.nio.file.Files
import java.nio.file.Path
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir


class AvrohuggerPluginFunctionalTest extends Specification {
  @Shared
  @TempDir
  Path projectDir
  @Shared
  Path buildFile
  @Shared
  Path mainAvroSource

  def setupSpec() {
    mainAvroSource = Files.createDirectories(projectDir.resolve("src/main/avro"))
    buildFile = projectDir.resolve("build.gradle")
    buildFile << ResourceUtils.read("sample.gradle")
  }

  def "run task without source"() {
    when:
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateAvroScala")
      .build()

    then:
    noExceptionThrown()
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.NO_SOURCE

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }

  def "task fails with an invalid schema"() {
    when:
    def file = mainAvroSource.resolve("sample.avsc")
    file.text = TestUtils.resource([name : 'FullName']).replace("\"type\": \"record\",", "")
    def buildResult = TestUtils.gradleRunner(projectDir, gradleVersion, "generateAvroScala").buildAndFail()

    then:
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.FAILED

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }
}

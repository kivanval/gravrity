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
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir

class GenerateTaskUpToDateFunctionalTest extends Specification {
  @Shared
  @TempDir
  Path projectDir
  @Shared
  Path mainAvroSource
  @Shared
  Path buildFile

  def setupSpec() {
    mainAvroSource = Files.createDirectories(projectDir.resolve("src/main/avro"))
    buildFile = projectDir.resolve("build.gradle")
    buildFile << ResourceUtils.read("sample.gradle")
  }

  def "second task call should have up to date outcome"() {
    given:
    def file = mainAvroSource.resolve("sample.avsc")
    def classname = 'FullName'
    file.text = TestUtils.resource([name: classname])
    def runner = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateAvroScala")

    when:
    BuildResult result = runner.build()

    then:
    result.task(":generateAvroScala").outcome == TaskOutcome.SUCCESS

    when:
    result = runner.build()

    then:
    result.task(":generateAvroScala").outcome == TaskOutcome.UP_TO_DATE

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }
}

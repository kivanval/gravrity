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

class GenerateTaskCreationFunctionalTest extends Specification{
  @Shared
  @TempDir
  Path projectDir
  @Shared
  Path mainAvroSource
  @Shared
  Path generatedOutputDir
  @Shared
  Path buildFile

  def setupSpec() {
    mainAvroSource = Files.createDirectories(projectDir.resolve("src/main/avro"))
    generatedOutputDir = Files.createDirectories(projectDir.resolve("build/generated/sources/avrohugger/scala/main"))
    buildFile = projectDir.resolve("build.gradle")
    buildFile << ResourceUtils.read("sample.gradle")
  }

  def "task creates correct scala class"() {
    when:
    def file = mainAvroSource.resolve("sample.avsc")
    def classname = 'FullName'
    file.text = TestUtils.resource([name: classname])
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateAvroScala")
      .build()

    then:
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.SUCCESS
    def filePath = generatedOutputDir.resolve("${classname}.scala")
    Files.exists(filePath)

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }
}

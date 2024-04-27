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


import io.github.kivanval.gradle.util.TestUtils
import java.nio.file.Files
import java.nio.file.Path
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification
import spock.lang.TempDir


class GenerateAvroScalaFunctionalTest extends Specification {
  @TempDir
  Path projectDir
  Path buildFile
  Path mainAvroSource
  Path generatedOutputDir

  def setup() {
    mainAvroSource = Files
      .createDirectories(projectDir.resolve("src/main/avro"))
    generatedOutputDir = Files
      .createDirectories(projectDir.resolve("build/generated/sources/avrohugger/scala/main"))
    buildFile = projectDir.resolve("build.gradle")
    buildFile << TestUtils.resource("sample.gradle")
  }

  def "run task without source"() {
    when:
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateAvroScala")
      .build()

    then:
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.NO_SOURCE

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }

  def "run task with .avsc source"() {
    when:
    def file = mainAvroSource.resolve("sample.avsc")
    def classname = 'Name'
    file.text = TestUtils.resource(name: classname, 'template.avsc')
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateAvroScala")
      .build()

    then:
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.SUCCESS
    Files.list(generatedOutputDir)
      .findFirst()
      .isPresent()

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }

  def "task fails with an invalid schema"() {
    when:
    def file = mainAvroSource.resolve("sample.avsc")
    file.text = TestUtils.resource(name: 'FullName', 'template.avsc').replace("\"type\": \"record\",", "")
    def buildResult = TestUtils.gradleRunner(projectDir, gradleVersion, "generateAvroScala").buildAndFail()

    then:
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.FAILED

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }

  def "task creates correct scala class"() {
    when:
    def file = mainAvroSource.resolve("sample.avsc")
    def classname = 'FullName'
    file.text = TestUtils.resource(name: classname, 'template.avsc')
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

  def "second task call should have up to date outcome"() {
    given:
    def file = mainAvroSource.resolve("sample.avsc")
    def classname = 'FullName'
    file.text = TestUtils.resource(name: classname, 'template.avsc')
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

  def "task ignores invalid files"() {
    when:
    def file = mainAvroSource.resolve("sample.txt")
    file.text = 'Hello'
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateAvroScala")
      .build()

    then:
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.SUCCESS
    Files.list(generatedOutputDir).toList().isEmpty()

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }

  def "task creates class for a large .avsc file"() {
    when:
    def file = mainAvroSource.resolve("large.avsc")
    def classname = 'Interaction'
    def namespace = 'com.example.analytics.event'
    file.text = TestUtils.resource("large.avsc", name : classname, namespace : namespace)
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateAvroScala")
      .build()

    then:
    buildResult.task(":generateAvroScala").outcome == TaskOutcome.SUCCESS

    def filePath = generatedOutputDir.resolve("${namespace.replace('.', '/')}/${classname}.scala")
    Files.exists(filePath)

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }
}

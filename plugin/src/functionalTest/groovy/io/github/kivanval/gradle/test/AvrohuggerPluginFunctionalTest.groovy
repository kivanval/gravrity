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
import java.nio.file.Paths
import org.gradle.testkit.runner.TaskOutcome
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
    buildFile << ResourceUtils.read("sample.gradle")
    settingsFile = new File(projectDir, "settings.gradle")
    settingsFile << ""
  }

  def "run task without source"() {
    when:
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateMainAvroScala")
      .build()

    then:
    noExceptionThrown()
    buildResult.task(":generateMainAvroScala").outcome == TaskOutcome.NO_SOURCE

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }

  def "run task with .avpr source"() {
    when:
    def sourceDir = new File(projectDir, "src/main/avro")
    sourceDir.mkdirs()
    def file = new File(sourceDir, "sample.avpr")
    file << ResourceUtils.read("sample.avpr")
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "generateMainAvroScala")
      .build()

    then:
    buildResult.task(":generateMainAvroScala").outcome == TaskOutcome.SUCCESS
    Files.list(Path.of(projectDir.toString(), "build/generated/sources/avrohugger/scala/main"))
      .findFirst()
      .isPresent()

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }
}

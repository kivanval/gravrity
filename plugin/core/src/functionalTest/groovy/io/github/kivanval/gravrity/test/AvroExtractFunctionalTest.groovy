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
package io.github.kivanval.gravrity.test

import io.github.kivanval.gravrity.util.TestUtils
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import org.gradle.internal.impldep.com.google.common.io.Resources
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification
import spock.lang.TempDir

class AvroExtractFunctionalTest extends Specification {
  @TempDir
  Path projectDir
  Path buildFile
  Path extractedOutputDir

  def setup() {
    extractedOutputDir = projectDir.resolve("build/extracted/sources/gravrity/avro/main")
    buildFile = projectDir.resolve("build.gradle")
    buildFile << TestUtils.resource("sample.gradle")
  }

  def "task extracts nested archive with avro files with files() configuration"() {
    given:
    def lib = Files.createDirectories(projectDir.resolve("lib"))
    def localArchive = lib.resolve('nestedSample.zip')
    def resPath = Path.of(Resources.getResource('nestedSample.zip').toURI())
    localArchive << Files.readAllBytes(resPath)
    buildFile << '''
          dependencies {
              avro files('lib/nestedSample.zip')
          }
        '''

    when:
    def buildResult = TestUtils
      .gradleRunner(projectDir, gradleVersion, "extractAvro")
      .build()


    then:
    def extractedFileNames = new HashSet<Path>()
    Files.walkFileTree(extractedOutputDir, new SimpleFileVisitor<Path>() {
        @Override
        FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
          extractedFileNames.add(extractedOutputDir.relativize(file))
          return FileVisitResult.CONTINUE
        }
      })
    buildResult.task(":extractAvro").outcome == TaskOutcome.SUCCESS
    extractedFileNames.size() == 6
    extractedFileNames.contains(Path.of('example', 'user_activity.avsc'))
    extractedFileNames.contains(Path.of('example', 'yelp_review.avsc'))
    extractedFileNames.contains(Path.of('example', 'truck_events.avsc'))
    extractedFileNames.contains(Path.of('user_profile.avsc'))
    extractedFileNames.contains(Path.of('user_activity.avsc'))
    extractedFileNames.contains(Path.of('page_view.avsc'))

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }

  def "task extracts nested archive with avro files with fileTree() configuration"() {
    given:
    def libDir1 = Files.createDirectories(projectDir.resolve("lib/directory1"))
    def libDir2 = Files.createDirectories(projectDir.resolve("lib/directory2"))
    Files.createFile(libDir1.resolve('sample.avsc'))
    Files.createFile(libDir2.resolve('sample.avsc'))
    buildFile << '''
          dependencies {
              avro fileTree('lib')
          }
        '''

    when:
    def buildResult = TestUtils
            .gradleRunner(projectDir, gradleVersion, "extractAvro")
            .build()


    then:
    def extractedFileNames = new HashSet<Path>()
    Files.walkFileTree(extractedOutputDir, new SimpleFileVisitor<Path>() {
      @Override
      FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        extractedFileNames.add(extractedOutputDir.relativize(file))
        return FileVisitResult.CONTINUE
      }
    })
    buildResult.task(":extractAvro").outcome == TaskOutcome.SUCCESS
    extractedFileNames.size() == 2
    extractedFileNames.contains(Path.of('directory1', 'sample.avsc'))
    extractedFileNames.contains(Path.of('directory2', 'sample.avsc'))

    where:
    gradleVersion << TestUtils.GRADLE_VERSIONS
  }
}

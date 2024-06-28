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

import org.gradle.api.file.ConfigurableFileTree
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class AvroFileVisitorTest extends Specification {

  @TempDir
  Path tmp

  def "file visitor collects all avro files"() {
    given:
    def project = ProjectBuilder.builder().build()
    def file = Files.createFile(tmp.resolve("test.avsc"))
    def inputFiles = project.objects.fileTree().from(tmp)
    def outputFiles = project.objects.fileTree()
    def avroVisitor = new AvroFileVisitor(outputFiles)
    when:
    inputFiles.visit(avroVisitor)

    then:
    outputFiles.files.contains(file.toFile())
  }
}

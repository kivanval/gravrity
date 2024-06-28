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

import groovy.transform.CompileStatic
import java.nio.file.Path
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor

@CompileStatic
class AvroFileVisitor implements FileVisitor {

  private ConfigurableFileCollection avroFiles

  AvroFileVisitor(ConfigurableFileCollection files) {
    avroFiles = files
  }

  @Override
  void visitDir(FileVisitDetails dirDetails) {
    print("aaaa")
  }

  @Override
  void visitFile(FileVisitDetails fileDetails) {
    def fileName = fileDetails.name
    if ((fileName.endsWith('.avsc') || fileName.endsWith('.avdl') || fileName.endsWith('.avpr'))) {
      avroFiles.from(fileDetails.file)
    }
  }
}

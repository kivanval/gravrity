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
import org.gradle.api.Project
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor
import org.gradle.api.file.RelativePath

@CompileStatic
class AvroFileVisitor implements FileVisitor {

  Map<Path, RelativePath> targetFiles

  Optional<RelativePath> parentRelativePath

  private Project project

  AvroFileVisitor(Project project) {
    this(new HashMap<Path, RelativePath>(), project, Optional.<RelativePath>empty())
  }

  private AvroFileVisitor(Map<Path, RelativePath> targetFiles, Project project, Optional<RelativePath> parentRelativePath) {
    this.project = project
    this.targetFiles = targetFiles
    this.parentRelativePath = parentRelativePath
  }

  @Override
  void visitDir(FileVisitDetails dirDetails) {
  }

  @Override
  void visitFile(FileVisitDetails fileDetails) {
    def fileName = fileDetails.name

    if ((fileName.endsWith('.avsc') || fileName.endsWith('.avdl') || fileName.endsWith('.avpr'))) {
      targetFiles.put(
        fileDetails.file.toPath(),
        parentRelativePath
        .map {
          it.parent.append(fileDetails.relativePath)
        }.orElse(fileDetails.relativePath)
        )
    } else {
      processArchive(fileDetails, Optional.of(fileDetails.relativePath))
    }
  }

  private void processArchive(FileVisitDetails fileDetails, Optional<RelativePath> relativePath) {
    def fileName = fileDetails.name
    def avroVisitor = new AvroFileVisitor(targetFiles, project, relativePath)
    if (fileName.endsWith('.jar') || fileName.endsWith('.zip') || fileName.endsWith('.aar')) {
      project.zipTree(fileDetails.file).visit(avroVisitor)
    } else if (fileName.endsWith('.tar')
      || fileName.endsWith('.tar.gz')
      || fileName.endsWith('.tar.bz2')
      || fileName.endsWith('.tgz')) {
      project.tarTree(fileDetails.file).visit(avroVisitor)
    }
  }
}

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
package io.github.kivanval.gradle.task

import groovy.transform.CompileStatic
import io.github.kivanval.gradle.util.AvroFileVisitor
import javax.inject.Inject
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.FileCopyDetails
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction

@CompileStatic
class AvroExtract extends SourceTask {

  @OutputDirectory
  final DirectoryProperty destinationDirectory

  @Inject
  AvroExtract() {
    this.destinationDirectory = project.objects.directoryProperty()
  }

  @TaskAction
  extract() {
    def avroVisitor = new AvroFileVisitor(project)

    source.visit(avroVisitor)
    project.copy { CopySpec it ->
      it.includeEmptyDirs = false
      it.from(avroVisitor.targetFiles.keySet())
      it.into(destinationDirectory)
      it.duplicatesStrategy = DuplicatesStrategy.INCLUDE
      it.eachFile { FileCopyDetails fileCopyDetails ->
        relativePath = avroVisitor.targetFiles[fileCopyDetails.file.toPath()]
      }
    }
  }
}

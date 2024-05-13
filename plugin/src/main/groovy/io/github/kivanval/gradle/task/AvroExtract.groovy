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
package io.github.kivanval.gradle.task

import groovy.transform.CompileStatic
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.CopySpec
import org.gradle.api.file.DeleteSpec
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.FileTree
import org.gradle.api.internal.file.FileOperations
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.WorkResult
import org.gradle.api.tasks.util.PatternFilterable
import org.gradle.api.tasks.util.PatternSet

class AvroExtract extends DefaultTask {
  @InputFiles
  final ConfigurableFileCollection inputFiles

  @OutputDirectory
  final DirectoryProperty destinationDirectory

  @Inject
  AvroExtract() {
    this.inputFiles = project.objects.fileCollection()
    this.destinationDirectory = project.objects.directoryProperty()
  }

  @TaskAction
  extract() {
    project.delete {
      it.delete(destinationDirectory)
    }
    FileTree inputAvro = getInputAvroFiles()
    project.copy {
      it.includeEmptyDirs = false
      it.from(inputAvro)
      it.into(destinationDirectory)
      it.duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
  }

  private FileTree getInputAvroFiles() {
    instantiateFilteredAvro().asFileTree
      .matching { PatternFilterable pattern -> pattern.include("**/*.avsc", "**/*.avdl", "**/*.avpr") }
  }

  private FileCollection instantiateFilteredAvro() {
    PatternSet avroFilter = new PatternSet().include("**/*.avsc", "**/*.avdl", "**/*.avpr")
    return project.objects.fileCollection()
      .from(inputFiles.filter { false })
      .from(
      inputFiles.getElements().map { elements ->
        Set<Object> avroInputs = [] as Set
        for (FileSystemLocation e : elements) {
          File file = e.asFile
          if (file.isDirectory()) {
            avroInputs.add(file)
          } else if ((file.path.endsWith('.avsc') || file.path.endsWith('.avdl') || file.path.endsWith('.avpr'))) {
            avroInputs.add(file)
          } else if (file.path.endsWith('.jar') || file.path.endsWith('.zip')) {
            avroInputs.add(project.zipTree(file.path).matching(avroFilter))
          } else if (file.path.endsWith('.aar')) {
            FileCollection zipTree = project.zipTree(file.path).filter { File it -> it.path.endsWith('.jar') }
            zipTree.each { entry ->
              avroInputs.add(project.zipTree(entry).matching(avroFilter))
            }
          } else if (file.path.endsWith('.tar')
            || file.path.endsWith('.tar.gz')
            || file.path.endsWith('.tar.bz2')
            || file.path.endsWith('.tgz')) {
            avroInputs.add(project.tarTree(file.path).matching(avroFilter))
          }
        }
        return avroInputs
      })
  }
}

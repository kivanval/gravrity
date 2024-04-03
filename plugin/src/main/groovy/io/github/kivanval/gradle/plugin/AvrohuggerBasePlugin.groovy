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
package io.github.kivanval.gradle.plugin

import groovy.transform.CompileStatic
import io.github.kivanval.gradle.extension.AvrohuggerExtension
import io.github.kivanval.gradle.task.GenerateAvroTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.tasks.DefaultSourceSetOutput
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.internal.Cast
import org.gradle.util.internal.GUtil

@CompileStatic
class AvrohuggerBasePlugin implements Plugin<Project> {

  @Override
  void apply(final Project project) {
    project.pluginManager.apply(JavaPlugin)

    configureSourceSetDefaults(project)
    configureExtension(project)
  }

  private static void configureSourceSetDefaults(final Project project) {
    final def sourceSets = project.extensions.getByType(SourceSetContainer)
    sourceSets.configureEach { SourceSet sourceSet ->
      final def displayName = GUtil.toWords(sourceSet.name) + " Avro source"
      final def avro = project.objects.sourceDirectorySet("avro", displayName)
      sourceSet.extensions.add(SourceDirectorySet, "avro", avro)
      avro.srcDir("src/" + sourceSet.name + "/" + avro.name)

      sourceSet.allSource.source(avro)

      final def avroScalaGeneratedPath = "generated/sources/avrohugger/scala/" + sourceSet.name
      final def outputDir = project.layout.buildDirectory.dir(avroScalaGeneratedPath)

      final def output = Cast.cast(DefaultSourceSetOutput, sourceSet.output)
      output.generatedSourcesDirs.from(outputDir)

      registerGenerateAvroTask(project, sourceSet, avro, outputDir)
    }
  }

  private static final String AVROHUGGER_EXTENSION_NAME = "avrohugger"

  private static void configureExtension(final Project project) {
    project.extensions.create(AVROHUGGER_EXTENSION_NAME, AvrohuggerExtension)
  }

  private static registerGenerateAvroTask(
    final Project project,
    final SourceSet sourceSet,
    final SourceDirectorySet sourceDirectorySet,
    final Provider<Directory> outputBaseDir
  ) {
    return project.tasks.register("generate${GUtil.toCamelCase(sourceSet.name)}AvroScala", GenerateAvroTask) {
      // TODO Create a better description
      it.description = "Generates " + sourceDirectorySet + "."
      it.outputDir.set(outputBaseDir)
    }
  }
}

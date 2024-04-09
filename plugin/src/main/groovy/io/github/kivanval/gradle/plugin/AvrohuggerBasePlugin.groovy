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
import io.github.kivanval.gradle.task.GenerateAvroScala
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.tasks.DefaultSourceSetOutput
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskProvider
import org.gradle.internal.Cast
import org.gradle.util.internal.GUtil

@CompileStatic
class AvrohuggerBasePlugin implements Plugin<Project> {
  private final Project project
  private final ObjectFactory objects

  @Inject
  AvrohuggerBasePlugin(Project project, ObjectFactory objectFactory) {
    this.project = project
    this.objects = objectFactory
  }

  @Override
  void apply(final Project project) {
    project.pluginManager.apply(JavaPlugin)
    configureSourceSetDefaults(configureExtension())
  }

  private static final String AVROHUGGER_EXTENSION_NAME = "avrohugger"

  private AvrohuggerExtension configureExtension() {
    project.extensions.create(AVROHUGGER_EXTENSION_NAME, AvrohuggerExtension)
  }

  private void configureSourceSetDefaults(AvrohuggerExtension avrohuggerExtension) {
    project.extensions.getByType(JavaPluginExtension)
      .sourceSets.configureEach { SourceSet sourceSet ->
        final def avro = createAvroSourceDirectorySet(sourceSet)

        sourceSet.extensions.add(SourceDirectorySet, avro.name, avro)
        sourceSet.allJava.source(avro)

        Cast.cast(DefaultSourceSetOutput, sourceSet.output)
          .generatedSourcesDirs.from(avro.destinationDirectory)

        configureGenerateAvroScala(sourceSet, avro, avrohuggerExtension)
      }
  }

  private SourceDirectorySet createAvroSourceDirectorySet(SourceSet sourceSet) {
    final def name = "avro"
    final def displayName = GUtil.toWords(sourceSet.name) + " Avro source"
    // TODO Use a custom SourceDirectorySet when versions < 8.0 will not be supported
    final def avro = objects.sourceDirectorySet(name, displayName)
    avro.include("**./*.avdl", "**./*.avpr", "**/*.avro", "**/*.avsc")

    final def generatedScalaSrcDir = project.layout.buildDirectory
      .dir("generated/sources/avrohugger/scala/" + sourceSet.name)
    avro.destinationDirectory.convention(generatedScalaSrcDir)

    avro.srcDir("src/" + sourceSet.name + "/" + avro.name)
  }

  private TaskProvider<GenerateAvroScala> configureGenerateAvroScala(
    final SourceSet sourceSet,
    final SourceDirectorySet avroSource,
    // TODO Map extension properties to task properties
    final AvrohuggerExtension avrohuggerExtension
  ) {
    final def generateAvroScala = project.tasks
      .register("generate${GUtil.toCamelCase(sourceSet.name)}AvroScala", GenerateAvroScala) {
        it.description = "Generates " + avroSource + "."
        it.format.set(avrohuggerExtension.format)
        it.namespaceMapping.set(avrohuggerExtension.namespaceMapping)
        it.restrictedFieldNumber.set(avrohuggerExtension.restrictedFieldNumber)
        it.source(avroSource)
      }
    avroSource.compiledBy(generateAvroScala, {it.destinationDirectory})
    generateAvroScala
  }
}

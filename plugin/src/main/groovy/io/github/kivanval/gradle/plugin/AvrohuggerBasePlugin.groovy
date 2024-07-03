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
import io.github.kivanval.gradle.task.AvroExtract
import io.github.kivanval.gradle.task.GenerateAvroScala
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.tasks.DefaultSourceSetOutput
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.scala.ScalaBasePlugin
import org.gradle.api.tasks.ScalaSourceDirectorySet
import org.gradle.api.tasks.SourceSet
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
    project.pluginManager.apply(ScalaBasePlugin)
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

        avro.destinationDirectory.with {
          Cast.cast(DefaultSourceSetOutput, sourceSet.output).generatedSourcesDirs.from(it)
          sourceSet.extensions.getByType(ScalaSourceDirectorySet).srcDir(it)
        }

        createGenerateAvroScalaTask(sourceSet, avro, avrohuggerExtension)
        Configuration config = createAvroConfiguration(sourceSet)
        createAvroExtractTask(sourceSet, avro,  config)
      }
  }

  private Configuration createAvroConfiguration(SourceSet sourceSet) {
    String avroConfigName = getConfigName(sourceSet.name, "avro")
    return project.configurations.create(avroConfigName) { Configuration it ->
      it.visible = false
      it.transitive = true
    }
  }

  static String getConfigName(String sourceSetName, String type) {
    return sourceSetName == SourceSet.MAIN_SOURCE_SET_NAME
      ? type : sourceSetName + type.capitalize()
  }


  private SourceDirectorySet createAvroSourceDirectorySet(SourceSet sourceSet) {
    final def displayName = "${GUtil.toWords(sourceSet.name)} Avro source"
    // TODO Use a custom SourceDirectorySet when versions < 8.0 will not be supported
    final def avro = objects.sourceDirectorySet("avro", displayName)
    avro.include("**./*.avdl", "**./*.avpr", "**/*.avsc")

    final def generatedAvrohuggerDir = project.layout.buildDirectory
      .dir("generated/sources/avrohugger/scala/${sourceSet.name}")
    avro.destinationDirectory.convention(generatedAvrohuggerDir)

    avro.srcDir("src/${sourceSet.name}/${avro.name}")
  }

  private void createGenerateAvroScalaTask(
    final SourceSet sourceSet,
    final SourceDirectorySet avroSource,
    final AvrohuggerExtension avrohuggerExtension
  ) {
    final def generateAvroScala = project.tasks
      .register(sourceSet.getTaskName("generate", "AvroScala"), GenerateAvroScala) {
        it.description = "Generates the $avroSource."
        it.format.set(avrohuggerExtension.format)
        it.namespaceMapping.set(avrohuggerExtension.namespaceMapping)
        it.restrictedFieldNumber.set(avrohuggerExtension.restrictedFieldNumber)
        it.source(avroSource.srcDirs)
      }
    avroSource
      .compiledBy(generateAvroScala, {it.destinationDirectory})
    project.tasks
      .named(sourceSet.getCompileTaskName("scala"))
      .configure {it.dependsOn(generateAvroScala)}
  }

  private void createAvroExtractTask(
    SourceSet sourceSet,
    SourceDirectorySet avroSource,
    Configuration config
  ) {
    final def extractedAvrohuggerDir = project.layout.buildDirectory
      .dir("${project.buildDir}/extracted/sources/avrohugger/avro/${sourceSet.name}")
    final def avroExtract = project.tasks
      .register(sourceSet.getTaskName("extract", "Avro"), AvroExtract) {
        it.description = "Extracts avro files/dependencies specified by configuration"
        it.destinationDirectory.convention(extractedAvrohuggerDir)
        it.source(config)
      }
    avroSource.srcDir(avroExtract)
    project.tasks
      .named(sourceSet.getTaskName("generate", "AvroScala"))
      .configure {it.dependsOn(avroExtract)}
  }
}

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
package io.github.kivanval.gravrity.plugin

import groovy.transform.CompileStatic
import io.github.kivanval.gravrity.extension.GravrityExtension
import io.github.kivanval.gravrity.source.AvroSourceDirectorySet
import io.github.kivanval.gravrity.task.AvroExtract
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.util.internal.GUtil

@CompileStatic
class GravrityBasePlugin implements Plugin<Project> {
  private final Project project

  @Inject
  GravrityBasePlugin(Project project) {
    this.project = project
  }

  @Override
  void apply(final Project project) {
    project.pluginManager.apply(JavaPlugin)
    configureExtension()
    configureSourceSetDefaults()
  }

  public static final String PLUGIN_NAME = "gravrity"

  public static final String EXTENSION_NAME = PLUGIN_NAME

  private GravrityExtension configureExtension() {
    project.extensions.create(EXTENSION_NAME, GravrityExtension)
  }

  private void configureSourceSetDefaults() {
    project.extensions.getByType(JavaPluginExtension)
      .sourceSets.configureEach { SourceSet sourceSet ->
        final def avro = createAvroSourceDirectorySet(sourceSet)
        final def config = createAvroConfiguration(sourceSet)
        createAvroExtractTask(sourceSet, avro, config)
      }
  }

  private Configuration createAvroConfiguration(SourceSet sourceSet) {
    String avroConfigName = getConfigName(sourceSet.name, "avro")
    return project.configurations.create(avroConfigName) { Configuration it ->
      it.visible = false
    }
  }

  static String getConfigName(String sourceSetName, String type) {
    return sourceSetName == SourceSet.MAIN_SOURCE_SET_NAME
      ? type : sourceSetName + type.capitalize()
  }


  private SourceDirectorySet createAvroSourceDirectorySet(SourceSet sourceSet) {
    final def displayName = "${GUtil.toWords(sourceSet.name)} Avro source"
    // TODO Use a custom SourceDirectorySet when versions < 8.0 will not be supported
    final def avro = project.objects.newInstance(
      AvroSourceDirectorySet, project.objects.sourceDirectorySet("avro", displayName))
    avro.include("**./*.avdl", "**./*.avpr", "**/*.avsc")
    avro.srcDir("src/${sourceSet.name}/${avro.name}")

    sourceSet.extensions.add(AvroSourceDirectorySet, avro.name, avro)
    sourceSet.allJava.source(avro)
    sourceSet.resources.source(avro)

    avro
  }


  private void createAvroExtractTask(
    SourceSet sourceSet,
    SourceDirectorySet avro,
    Configuration config) {
    final def extractedDir = project.layout.buildDirectory
      .dir("${project.buildDir}/extracted/sources/$PLUGIN_NAME/avro/${sourceSet.name}")
    final def avroExtract = project.tasks
      .register(sourceSet.getTaskName("extract", "Avro"), AvroExtract) {
        it.description = "Extracts avro files/dependencies specified by configuration"
        it.destinationDirectory.convention(extractedDir)
        it.source(config)
      }
    project.tasks.named(sourceSet.getTaskName("process", "resources"), ProcessResources).configure {
      it.mustRunAfter(avroExtract)
    }
    avro.srcDir(extractedDir)

    avroExtract
  }
}

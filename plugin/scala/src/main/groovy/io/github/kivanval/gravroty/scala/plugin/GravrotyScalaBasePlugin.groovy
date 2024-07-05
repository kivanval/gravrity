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
package io.github.kivanval.gravroty.scala.plugin

import groovy.transform.CompileStatic
import io.github.kivanval.gravroty.extension.GravrotyExtension
import io.github.kivanval.gravroty.plugin.GravrotyBasePlugin
import io.github.kivanval.gravroty.scala.extension.AvrohuggerExtension
import io.github.kivanval.gravroty.scala.task.GenerateAvroScala
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.tasks.DefaultSourceSetOutput
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.scala.ScalaBasePlugin
import org.gradle.api.tasks.ScalaSourceDirectorySet
import org.gradle.api.tasks.SourceSet
import org.gradle.internal.Cast

@CompileStatic
class GravrotyScalaBasePlugin implements Plugin<Project> {
  private final Project project

  @Inject
  GravrotyScalaBasePlugin(Project project) {
    this.project = project
  }

  @Override
  void apply(final Project project) {
    project.pluginManager.with {
      it.apply(GravrotyBasePlugin)
      it.apply(ScalaBasePlugin)
    }

    configureSourceSetDefaults(configureExtension())
  }

  private static final String EXTENSION_NAME = "scala"

  private AvrohuggerExtension configureExtension() {
    project.extensions.getByType(GravrotyExtension)
      .extensions.create(EXTENSION_NAME, AvrohuggerExtension)
  }

  private void configureSourceSetDefaults(AvrohuggerExtension avrohuggerExtension) {
    project.extensions.getByType(JavaPluginExtension)
      .sourceSets.configureEach { SourceSet sourceSet ->
        final def avro = configureAvroSourceDirectorySet(sourceSet)

        createGenerateAvroScalaTask(sourceSet, avro, avrohuggerExtension)
      }
  }

  private SourceDirectorySet configureAvroSourceDirectorySet(SourceSet sourceSet) {
    // TODO Use a custom SourceDirectorySet when versions < 8.0 will not be supported
    final def avro = sourceSet.extensions.getByType(SourceDirectorySet)

    final def generatedAvrohuggerDir = project.layout.buildDirectory
      .dir("generated/sources/${GravrotyBasePlugin.PLUGIN_NAME}/scala/${sourceSet.name}")
    avro.destinationDirectory.convention(generatedAvrohuggerDir)

    avro.destinationDirectory.with {
      Cast.cast(DefaultSourceSetOutput, sourceSet.output).generatedSourcesDirs.from(it)
      sourceSet.extensions.getByType(ScalaSourceDirectorySet).srcDir(it)
    }

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

    avroSource.compiledBy(generateAvroScala, {it.destinationDirectory})

    project.tasks.with {
      it.named(sourceSet.getCompileTaskName("scala")).configure {it.dependsOn(generateAvroScala)}
      it.named(sourceSet.getTaskName("extract", "avro")).configure {it.dependsOn(generateAvroScala)}
    }
  }
}

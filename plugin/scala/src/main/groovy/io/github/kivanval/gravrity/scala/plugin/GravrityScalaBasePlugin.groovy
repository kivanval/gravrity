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
package io.github.kivanval.gravrity.scala.plugin

import groovy.transform.CompileStatic
import io.github.kivanval.gravrity.extension.GravrityExtension
import io.github.kivanval.gravrity.plugin.GravrityCoreBasePlugin
import io.github.kivanval.gravrity.scala.extension.AvrohuggerExtension
import io.github.kivanval.gravrity.scala.task.GenerateAvroScala
import io.github.kivanval.gravrity.source.DefaultAvroSourceDirectorySet
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
class GravrityScalaBasePlugin implements Plugin<Project> {
  private final Project project

  @Inject
  GravrityScalaBasePlugin(Project project) {
    this.project = project
  }

  @Override
  void apply(final Project project) {
    project.pluginManager.with {
      it.apply(GravrityCoreBasePlugin)
      it.apply(ScalaBasePlugin)
    }

    configureSourceSetDefaults(configureExtension())
  }

  private static final String EXTENSION_NAME = "scala"

  private AvrohuggerExtension configureExtension() {
    project.extensions.getByType(GravrityExtension)
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
    final def avro = sourceSet.extensions.getByType(DefaultAvroSourceDirectorySet)

    final def generatedOutputDir = project.layout.buildDirectory
      .dir("generated/sources/${GravrityCoreBasePlugin.PLUGIN_NAME}/scala/${sourceSet.name}")

    avro.destinationDirectory.with {
      it.convention(generatedOutputDir)
      Cast.cast(DefaultSourceSetOutput, sourceSet.output).generatedSourcesDirs.from(it)
      sourceSet.extensions.getByType(ScalaSourceDirectorySet).srcDir(it)
    }
    avro
  }

  private void createGenerateAvroScalaTask(
    final SourceSet sourceSet,
    final SourceDirectorySet avro,
    final AvrohuggerExtension avrohuggerExtension
  ) {
    final def generateAvroScala = project.tasks
      .register(sourceSet.getTaskName("generate", "AvroScala"), GenerateAvroScala) {
        it.description = "Generates the $avro."
        it.format.set(avrohuggerExtension.format)
        it.namespaceMapping.set(avrohuggerExtension.namespaceMapping)
        it.restrictedFieldNumber.set(avrohuggerExtension.restrictedFieldNumber)
        it.source(avro)
      }

    avro.compiledBy(generateAvroScala, {it.destinationDirectory})

    project.tasks.named(sourceSet.getCompileTaskName("scala")).configure {
      it.dependsOn(generateAvroScala)
    }
    generateAvroScala.configure {
      it.dependsOn(project.tasks.named(sourceSet.getTaskName("extract", "avro")))
    }
  }
}

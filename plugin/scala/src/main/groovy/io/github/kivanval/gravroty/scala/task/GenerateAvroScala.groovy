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
package io.github.kivanval.gravroty.scala.task

import avrohugger.Generator
import avrohugger.filesorter.AvdlFileSorter
import avrohugger.filesorter.AvscFileSorter
import groovy.transform.CompileStatic
import io.github.kivanval.gravroty.scala.avrohugger.format.SourceFormat
import io.github.kivanval.gravroty.scala.avrohugger.format.Standard
import io.github.kivanval.gravroty.scala.util.DependencyUtils
import javax.inject.Inject
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import scala.Option
import scala.collection.immutable.Map as ScalaMap
import scala.collection.immutable.Seq
import scala.jdk.javaapi.CollectionConverters

@CompileStatic
@CacheableTask
class GenerateAvroScala extends SourceTask {
  @Input
  final Property<SourceFormat> format

  @Input
  final Property<Boolean> restrictedFieldNumber

  @Input
  final MapProperty<String, String> namespaceMapping

  @OutputDirectory
  final DirectoryProperty destinationDirectory

  @Inject
  GenerateAvroScala() {
    this.format = project.objects.property(SourceFormat).convention(new Standard())
    this.namespaceMapping = project.objects.mapProperty(String, String).convention(Map.of())
    // TODO It may be worth adding checks for version <= 2.10.*, but I don't know if it makes sense
    this.restrictedFieldNumber = project.objects.property(Boolean).convention(false)
    this.destinationDirectory = project.objects.directoryProperty()
  }

  @TaskAction
  generate() {
    def format = format.get()

    def generator = new Generator(
      format.origin,
      Option.apply(format.types.origin),
      ScalaMap.from(CollectionConverters.asScala(namespaceMapping.get())),
      restrictedFieldNumber.get(),
      Thread.currentThread().contextClassLoader,
      DependencyUtils.findScalaVersion(project)
      )

    def destinationDirectory = destinationDirectory
      .asFile
      .get()
      .toString()

    def sortedSources = [
      AvscFileSorter.sortSchemaFiles(Seq.from(CollectionConverters.asScala(source.matching {
        include "**/*.avsc"
      }))),
      AvdlFileSorter.sortSchemaFiles(Seq.from(CollectionConverters.asScala(source.matching {
        include "**/*.avdl"
      }))),
      Seq.from(CollectionConverters.asScala(source.matching {
        include "**/*.avpr"
      }))
    ]

    sortedSources.forEach {
      it.foreach { source ->
        generator.fileToFile(source, destinationDirectory)
      }
    }
  }
}

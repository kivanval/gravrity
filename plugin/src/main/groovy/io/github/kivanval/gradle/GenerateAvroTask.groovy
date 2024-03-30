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
package io.github.kivanval.gradle

import avrohugger.Generator
import groovy.transform.CompileStatic
import io.github.kivanval.avrohugger.format.SourceFormat
import io.github.kivanval.avrohugger.format.Standard
import io.github.kivanval.gradle.util.DependencyUtils
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import scala.Option
import scala.collection.immutable.Map as ScalaMap
import scala.jdk.javaapi.CollectionConverters

@CompileStatic
@CacheableTask
class GenerateAvroTask extends SourceTask {
  private final ObjectFactory objects

  @Input
  final Property<SourceFormat> format

  @Input
  final Property<Boolean> restrictedFieldNumber

  @Input
  final MapProperty<String, String> namespaceMapping

  @OutputDirectory
  final DirectoryProperty outputDir

  @Inject
  GenerateAvroTask(ObjectFactory objects) {
    this.objects = objects

    this.format = objects.property(SourceFormat).convention(new Standard())

    this.namespaceMapping = objects.mapProperty(String, String).convention(Map.of())

    // TODO It may be worth adding checks for version <= 2.10.*, but I don't know if it makes sense
    this.restrictedFieldNumber = objects.property(Boolean).convention(false)
    this.outputDir = objects.directoryProperty()
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

    def outputDir = outputDir
      .map { Directory it -> it.asFile.toString() }
      .getOrElse(generator.defaultOutputDir())
  }
}

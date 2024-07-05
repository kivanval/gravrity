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
package io.github.kivanval.gradle.plugin

import io.github.kivanval.gravroty.scala.plugin.AvrohuggerPlugin
import java.nio.file.Paths
import org.gradle.api.tasks.ScalaSourceDirectorySet
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class AvrohuggerPluginTest extends Specification {

  def "plugin should have avro default settings in sourceSets"() {
    given:
    def project = ProjectBuilder.builder().build()
    def buildDir = project.layout.buildDirectory.asFile.get()
      .toString()
    def generatedSourceDirs = Paths.get(buildDir, "generated/sources/avrohugger/scala/$sourceSetName")
      .toString()
    def srcDir = Paths.get(project.projectDir.toString(), "src/$sourceSetName/avro")
      .toString()
    def extractedSrcDir = Paths.get(buildDir, "/extracted/sources/avrohugger/avro/$sourceSetName").toString()

    when:
    project.pluginManager.apply(AvrohuggerPlugin)

    then:
    def sourceSet = project.sourceSets.getByName(sourceSetName)

    sourceSet.avro.srcDirs.collect { it.toString() }.contains(srcDir)
    sourceSet.avro.srcDirs.collect { it.toString() }.contains(extractedSrcDir)
    sourceSet.avro.destinationDirectory.get().toString() == generatedSourceDirs
    sourceSet.output.generatedSourcesDirs.collect { it.toString() }.contains(generatedSourceDirs)

    where:
    sourceSetName << ['main', 'test']
  }

  def "scala sourceSourceSet contains generated sources"() {
    given:
    def project = ProjectBuilder.builder().build()
    def buildDir = project.layout.buildDirectory.asFile.get()
      .toString()
    def generatedSourceDirs = Paths.get(buildDir, "generated/sources/avrohugger/scala/$sourceSetName")
      .toFile()

    when:
    project.pluginManager.with {
      apply(AvrohuggerPlugin)
    }

    then:
    def sourceSet = project.sourceSets.getByName(sourceSetName)
    def scalaSourceSet = sourceSet.extensions.getByType(ScalaSourceDirectorySet)
    scalaSourceSet.srcDirs.contains(generatedSourceDirs)

    where:
    sourceSetName << ['main', 'test']
  }
}
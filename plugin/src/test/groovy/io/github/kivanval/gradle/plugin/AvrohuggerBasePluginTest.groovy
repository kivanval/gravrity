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

import groovy.transform.CompileDynamic
import io.github.kivanval.avrohugger.format.SpecificRecord
import java.nio.file.Paths
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import org.gradle.util.internal.GUtil
import spock.lang.Specification

@CompileDynamic
class AvrohuggerBasePluginTest extends Specification {
  def "plugin should have avro default settings in sourceSets using ScalaPlugin"() {
    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.with {
      apply(ScalaPlugin)
      apply(AvrohuggerPlugin)
    }

    then:
    def sourceSet = project.sourceSets.getByName(sourceSetName)
    sourceSet.avro.srcDirs.collect { it.toString() } == [
      Paths.get(project.projectDir.toString(), "src", sourceSetName, "avro").toString()
    ]


    sourceSet.output.generatedSourcesDirs.collect { it.toString() }.contains(
    Paths.get(project.layout.buildDirectory.asFile.get().toString(), "generated", "sources", "avrohugger", "scala", sourceSetName).toString()
    )

    where:
    sourceSetName << ['main', 'test']
  }

  def "task should receive properties from extension"() {
    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.with {
      apply(ScalaPlugin)
      apply(AvrohuggerPlugin)
    }

    def specificFormat = new SpecificRecord()
    def specificNamespaceMapping = [
      'com.example' :  'org.example'
    ]
    project.avrohugger {
      format = specificFormat
      namespaceMapping = specificNamespaceMapping
      restrictedFieldNumber = true
    }

    then:
    def task = project.tasks.named("generate${GUtil.toCamelCase(sourceSetName)}AvroScala").get()
    task.format.getOrNull() == specificFormat
    task.namespaceMapping.getOrNull() == specificNamespaceMapping
    task.restrictedFieldNumber.getOrNull() == true

    where:
    sourceSetName << ['main', 'test']
  }
}

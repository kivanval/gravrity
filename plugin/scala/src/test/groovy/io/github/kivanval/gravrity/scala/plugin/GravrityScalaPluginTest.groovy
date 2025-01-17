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

import org.gradle.api.tasks.ScalaSourceDirectorySet
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GravrityScalaPluginTest extends Specification {

  def "plugin configure generated sources"() {
    given:
    def project = ProjectBuilder.builder().build()
    def buildDir = project.layout.buildDirectory.get().asFile.toPath()
    def generatedSourceDirs = buildDir.resolve("generated/sources/gravrity/scala/$sourceSetName")
      .toFile()

    when:
    project.pluginManager.apply(GravrityScalaPlugin)

    then:
    def sourceSet = project.sourceSets.getByName(sourceSetName)
    def scalaSourceSet = sourceSet.extensions.getByType(ScalaSourceDirectorySet)

    sourceSet.avro.destinationDirectory.get().asFile == generatedSourceDirs
    sourceSet.output.generatedSourcesDirs.contains(generatedSourceDirs)
    scalaSourceSet.srcDirs.contains(generatedSourceDirs)

    where:
    sourceSetName << ['main', 'test']
  }
}

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
package io.github.kivanval.gradle.util

import io.github.kivanval.gradle.util.DependencyUtils
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class DependencyUtilsTest extends Specification {
  def "findScalaVersion can find scala2 version"() {
    given:
    def project = ProjectBuilder.builder().build()
    def scalaVersion = '2.13.12'


    when:
    project.pluginManager.apply(ScalaPlugin)
    project.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, scala2Library(scalaVersion))

    then:
    DependencyUtils.findScalaVersion(project) == scalaVersion
  }

  def "findScalaVersion can find scala3 version"() {
    given:
    def project = ProjectBuilder.builder().build()
    def scalaVersion = '3.0.1'

    when:
    project.pluginManager.apply(ScalaPlugin)
    project.dependencies.add(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, scala3Library(scalaVersion))

    then:
    DependencyUtils.findScalaVersion(project) == scalaVersion
  }

  def "findScalaVersion can find default version"() {
    given:
    def project = ProjectBuilder.builder().build()
    def scalaVersion = '2.13.11'


    when:
    project.pluginManager.apply(ScalaPlugin)
    project.ext['default.scala.version'] = scalaVersion

    then:
    DependencyUtils.findScalaVersion(project) == scalaVersion
  }

  private static String scala2Library(String version) {
    "${DependencyUtils.SCALA_GROUP}:${DependencyUtils.SCALA2_ARTIFACT}:$version"
  }

  private static String scala3Library(String version) {
    "${DependencyUtils.SCALA_GROUP}:${DependencyUtils.SCALA3_ARTIFACT}:$version"
  }
}

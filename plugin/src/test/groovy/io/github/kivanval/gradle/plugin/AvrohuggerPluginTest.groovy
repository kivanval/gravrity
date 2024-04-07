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

import io.github.kivanval.gradle.plugin.AvrohuggerBasePlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.Specification

class AvrohuggerPluginTest extends Specification {
  def "plugin applies base plugin"() {
    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.apply(AvrohuggerPlugin)

    then:
    project.plugins.hasPlugin(AvrohuggerBasePlugin)
  }

  @Ignore
  def "plugin registers generateAvroScala task"() {
    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.apply(AvrohuggerPlugin)

    then:
    project.tasks.named('generateAvroScala').present
  }
}
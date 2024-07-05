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

import io.github.kivanval.gravroty.scala.task.GenerateAvroScala
import org.gradle.api.plugins.scala.ScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class AvroScalaCompileTest extends Specification {
  // TODO refactor test
  def "plugin should have ability to add task"() {

    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.with {
      apply(ScalaPlugin)
      apply(AvrohuggerPlugin)
    }

    then:
    def task = project.tasks.named('avro').get()
    task instanceof GenerateAvroScala

    where:
    sourceSetName << ['main', 'test']
  }
}

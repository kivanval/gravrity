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
package io.github.kivanval.gravrity.scala.avrohugger.type

import avrohugger.types.*
import io.github.kivanval.gravrity.scala.plugin.GravrityScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class PrimitiveAvroScalaTypeValuesTest extends Specification {
  def "primitive types matches to values in avrohugger library"() {
    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.apply(GravrityScalaPlugin)

    then:
    with(project.gravrity.scala) {
      scalaInt == ScalaInt$.MODULE$
      scalaLong == ScalaLong$.MODULE$
      scalaFloat == ScalaFloat$.MODULE$
      scalaDouble == ScalaDouble$.MODULE$
      scalaBoolean == ScalaBoolean$.MODULE$
      scalaString == ScalaString$.MODULE$
      scalaNull == ScalaNull$.MODULE$
      scalaByteArray == ScalaByteArray$.MODULE$
    }
  }
}

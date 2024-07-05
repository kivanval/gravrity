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
package io.github.kivanval.gravrity.scala.type

import avrohugger.types.*
import io.github.kivanval.gravrity.scala.plugin.GravrityScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ComplexAvroScalaTypeValuesTest extends Specification {
  def "complex types matches to values in avrohugger library"() {
    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.with {
      apply(GravrityScalaPlugin)
    }

    then:
    with(project.gravrity.scala) {
      scalaCaseClassWrapper == ScalaCaseClassWrapper$.MODULE$
      scalaCaseClassWrapperWithSchema == ScalaCaseClassWrapperWithSchema$.MODULE$
      scalaCaseClass == ScalaCaseClass$.MODULE$
      scalaCaseClassWithSchema == ScalaCaseClassWithSchema$.MODULE$
      scalaEnumeration == ScalaEnumeration$.MODULE$
      javaEnum == JavaEnum$.MODULE$
      scalaCaseObjectEnum == ScalaCaseObjectEnum$.MODULE$
      enumAsScalaString == EnumAsScalaString$.MODULE$
      optionalShapelessCoproduct == OptionalShapelessCoproduct$.MODULE$
      optionShapelessCoproduct == OptionShapelessCoproduct$.MODULE$
      optionEitherShapelessCoproduct == OptionEitherShapelessCoproduct$.MODULE$
      scalaArray == ScalaArray$.MODULE$
      scalaList == ScalaList$.MODULE$
      scalaSeq == ScalaSeq$.MODULE$
      scalaVector == ScalaVector$.MODULE$
      scalaMap == ScalaMap$.MODULE$
      scalaADT == ScalaADT$.MODULE$
      noTypeGenerated == NoTypeGenerated$.MODULE$
    }
  }
}

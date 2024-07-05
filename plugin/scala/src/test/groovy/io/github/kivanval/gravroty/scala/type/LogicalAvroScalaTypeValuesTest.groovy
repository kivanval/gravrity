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
package io.github.kivanval.gravroty.scala.type

import avrohugger.types.*
import io.github.kivanval.gravroty.scala.plugin.GravrotyScalaPlugin
import org.gradle.testfixtures.ProjectBuilder
import scala.Option
import scala.math.BigDecimal
import spock.lang.Specification

class LogicalAvroScalaTypeValuesTest extends Specification {
  def "logical types matches to values in avrohugger library"() {
    given:
    def project = ProjectBuilder.builder().build()

    when:
    project.pluginManager.with {
      apply(GravrotyScalaPlugin)
    }

    then:
    with(project.gravroty.scala) {
      scalaBigDecimal == new ScalaBigDecimal(Option.empty())
      scalaBigDecimal(roundingMode.FLOOR()) == new ScalaBigDecimal(Option.apply(roundingMode.FLOOR()))
      scalaBigDecimalWithPrecision == new ScalaBigDecimalWithPrecision(Option.empty())
      scalaBigDecimalWithPrecision(roundingMode.UP()) == new ScalaBigDecimalWithPrecision(Option.apply(roundingMode.UP()))
      roundingMode == BigDecimal.RoundingMode$.MODULE$
      javaSqlDate == JavaSqlDate$.MODULE$
      javaTimeLocalDate == JavaTimeLocalDate$.MODULE$
      javaSqlTimestamp == JavaSqlTimestamp$.MODULE$
      javaTimeInstant == JavaTimeInstant$.MODULE$
      javaUuid == JavaUuid$.MODULE$
      javaSqlTime == JavaSqlTime$.MODULE$
      javaTimeLocalTime == JavaTimeLocalTime$.MODULE$
      javaTimeZonedDateTime == JavaTimeZonedDateTime$.MODULE$
      javaTimeLocalDateTime == JavaTimeLocalDateTime$.MODULE$
    }
  }
}

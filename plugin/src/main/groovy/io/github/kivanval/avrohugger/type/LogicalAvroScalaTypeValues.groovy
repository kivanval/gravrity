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
package io.github.kivanval.avrohugger.type

import avrohugger.types.*
import groovy.transform.CompileStatic
import scala.Enumeration
import scala.Option
import scala.math.BigDecimal

@CompileStatic
trait LogicalAvroScalaTypeValues {
  ScalaBigDecimal getScalaBigDecimal() {
    new ScalaBigDecimal(Option.empty())
  }

  ScalaBigDecimal scalaBigDecimal(Enumeration.Value roundingMode) {
    new ScalaBigDecimal(Option.apply(roundingMode))
  }

  ScalaBigDecimalWithPrecision getScalaBigDecimalWithPrecision() {
    new ScalaBigDecimalWithPrecision(Option.empty())
  }

  ScalaBigDecimalWithPrecision scalaBigDecimalWithPrecision(Enumeration.Value roundingMode) {
    new ScalaBigDecimalWithPrecision(Option.apply(roundingMode))
  }

  BigDecimal.RoundingMode$ getRoundingMode() {
    BigDecimal.RoundingMode$.MODULE$
  }

  JavaSqlDate$ getJavaSqlDate() {
    JavaSqlDate$.MODULE$
  }

  JavaTimeLocalDate$ getJavaTimeLocalDate() {
    JavaTimeLocalDate$.MODULE$
  }

  JavaSqlTimestamp$ getJavaSqlTimestamp() {
    JavaSqlTimestamp$.MODULE$
  }

  JavaTimeInstant$ getJavaTimeInstant() {
    JavaTimeInstant$.MODULE$
  }

  JavaUuid$ getJavaUuid() {
    JavaUuid$.MODULE$
  }

  JavaSqlTime$ getJavaSqlTime() {
    JavaSqlTime$.MODULE$
  }

  JavaTimeLocalTime$ getJavaTimeLocalTime() {
    JavaTimeLocalTime$.MODULE$
  }

  JavaTimeZonedDateTime$ getJavaTimeZonedDateTime() {
    JavaTimeZonedDateTime$.MODULE$
  }

  JavaTimeLocalDateTime$ getJavaTimeLocalDateTime() {
    JavaTimeLocalDateTime$.MODULE$
  }
}

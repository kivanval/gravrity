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
package io.github.kivanval.gravroty.scala.avrohugger.type

import avrohugger.types.*
import groovy.transform.CompileStatic

@CompileStatic
trait PrimitiveAvroScalaTypeValues {
  ScalaInt$ getScalaInt() {
    ScalaInt$.MODULE$
  }

  ScalaLong$ getScalaLong() {
    ScalaLong$.MODULE$
  }

  ScalaFloat$ getScalaFloat() {
    ScalaFloat$.MODULE$
  }

  ScalaDouble$ getScalaDouble() {
    ScalaDouble$.MODULE$
  }

  ScalaBoolean$ getScalaBoolean() {
    ScalaBoolean$.MODULE$
  }

  ScalaString$ getScalaString() {
    ScalaString$.MODULE$
  }

  ScalaNull$ getScalaNull() {
    ScalaNull$.MODULE$
  }

  ScalaByteArray$ getScalaByteArray() {
    ScalaByteArray$.MODULE$
  }
}

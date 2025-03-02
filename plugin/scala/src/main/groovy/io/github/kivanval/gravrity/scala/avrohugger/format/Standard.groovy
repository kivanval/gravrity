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
package io.github.kivanval.gravrity.scala.avrohugger.format


import avrohugger.format.Standard$
import avrohugger.format.abstractions.SourceFormat as OriginSourceFormat
import groovy.transform.CompileStatic
import io.github.kivanval.gravrity.scala.avrohugger.type.AvroScalaTypes

@CompileStatic
class Standard implements SourceFormat {
  final AvroScalaTypes types

  Standard() {
    this.types = new AvroScalaTypes(origin.defaultTypes())
  }

  OriginSourceFormat getOrigin() {
    Standard$.MODULE$
  }
}

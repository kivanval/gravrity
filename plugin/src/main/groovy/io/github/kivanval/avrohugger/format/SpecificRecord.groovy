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
package io.github.kivanval.avrohugger.format

import avrohugger.format.SpecificRecord$
import avrohugger.format.abstractions.SourceFormat as OriginSourceFormat
import groovy.transform.CompileStatic
import io.github.kivanval.avrohugger.type.AvroScalaTypes

@CompileStatic
class SpecificRecord implements SourceFormat {
  AvroScalaTypes types

  SpecificRecord() {
    this.types = new AvroScalaTypes(origin.defaultTypes())
  }

  OriginSourceFormat getOrigin() {
    SpecificRecord$.MODULE$
  }
}

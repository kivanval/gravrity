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
package io.github.kivanval.gravroty.scala.extension

import static org.gradle.util.internal.ConfigureUtil.configure

import groovy.transform.CompileStatic
import io.github.kivanval.gravroty.scala.avrohugger.format.SourceFormat
import io.github.kivanval.gravroty.scala.avrohugger.format.SpecificRecord
import io.github.kivanval.gravroty.scala.avrohugger.format.Standard
import io.github.kivanval.gravroty.scala.avrohugger.type.AvroScalaTypes
import org.gradle.api.Action

@CompileStatic
trait AvroSourceFormatOperations {
  SourceFormat getStandard() {
    new Standard()
  }

  SourceFormat getSpecificRecord() {
    new SpecificRecord()
  }

  SourceFormat standard(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
    def standard = standard
    configure(configureClosure, standard.types)
    standard
  }

  SourceFormat standard(Action<? super AvroScalaTypes> configureAction) {
    def standard = standard
    configureAction.execute(standard.types)
    standard
  }

  SourceFormat specificRecord(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
    def specificRecord = specificRecord
    configure(configureClosure, specificRecord.types)
    specificRecord
  }

  SourceFormat specificRecord(Action<? super AvroScalaTypes> configureAction) {
    def specificRecord = specificRecord
    configureAction.execute(specificRecord.types)
    specificRecord
  }
}

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
package io.github.kivanval.gradle

import static org.gradle.util.internal.ConfigureUtil.configure

import groovy.transform.CompileStatic
import io.github.kivanval.gradle.format.AvroScalaTypes
import io.github.kivanval.gradle.format.AvroSourceFormat
import io.github.kivanval.gradle.format.SpecificRecord
import io.github.kivanval.gradle.format.Standard
import org.gradle.api.Action

@CompileStatic
trait AvroSourceFormatOperations {
	AvroSourceFormat getStandard() {
		Standard.EMPTY
	}

	AvroSourceFormat getSpecificRecord() {
		SpecificRecord.EMPTY
	}

	AvroSourceFormat standard(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
		configure(configureClosure, standard.types)
		standard
	}

	AvroSourceFormat standard(Action<? super AvroScalaTypes> configureAction) {
		configureAction.execute(standard.types)
		standard
	}

	AvroSourceFormat specificRecord(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
		configure(configureClosure, specificRecord.types)
		specificRecord
	}

	AvroSourceFormat specificRecord(Action<? super AvroScalaTypes> configureAction) {
		configureAction.execute(specificRecord.types)
		specificRecord
	}
}

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
import org.gradle.api.Action

@CompileStatic
trait AvroSourceFormatOperations implements AvrohuggerExtensionBase {
	AvroSourceFormat standard(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
		sourceFormat(standard, configureClosure)
	}

	AvroSourceFormat standard(Action<? super AvroScalaTypes> configureAction) {
		sourceFormat(standard, configureAction)
	}

	AvroSourceFormat specificRecord(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
		sourceFormat(specificRecord, configureClosure)
	}

	AvroSourceFormat specificRecord(Action<? super AvroScalaTypes> configureAction) {
		sourceFormat(specificRecord, configureAction)
	}

	private AvroSourceFormat sourceFormat(AvroSourceFormat avroSourceFormat,
			Closure<?> configureClosure) {
		def sourceFormat = avroSourceFormat.copy
		sourceFormat.types.set(sourceFormat.types
				.map { avroScalaTypes ->
					configure(configureClosure, avroScalaTypes)
				})
		sourceFormat
	}

	private AvroSourceFormat sourceFormat(AvroSourceFormat avroSourceFormat,
			Action<? super AvroScalaTypes> configureAction) {
		def sourceFormat = avroSourceFormat.copy
		sourceFormat.types.set(sourceFormat.types
				.map { avroScalaTypes ->
					configureAction.execute(avroScalaTypes)
					avroScalaTypes
				})
		sourceFormat
	}
}

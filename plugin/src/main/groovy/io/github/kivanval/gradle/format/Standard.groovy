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
package io.github.kivanval.gradle.format

import avrohugger.format.Standard$
import avrohugger.format.abstractions.SourceFormat
import groovy.transform.CompileStatic

@CompileStatic
class Standard implements AvroSourceFormat {
	final SourceFormat sourceFormat
	AvroScalaTypes types


	Standard() {
		this.sourceFormat = Standard$.MODULE$
		this.types = new AvroScalaTypes(sourceFormat.defaultTypes())
	}

	static Standard EMPTY = new Standard()
}

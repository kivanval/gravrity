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

import avrohugger.types.*
import groovy.transform.CompileStatic

@CompileStatic
trait ComplexAvroScalaTypes {
	// fixed
	ScalaCaseClassWrapper$ getScalaCaseClassWrapper() {
		ScalaCaseClassWrapper$.MODULE$
	}

	ScalaCaseClassWrapperWithSchema$ getScalaCaseClassWrapperWithSchema() {
		ScalaCaseClassWrapperWithSchema$.MODULE$
	}

	// record
	ScalaCaseClass$ getScalaCaseClass() {
		ScalaCaseClass$.MODULE$
	}

	ScalaCaseClassWithSchema$ getScalaCaseClassWithSchema() {
		ScalaCaseClassWithSchema$.MODULE$
	}

	// enum
	ScalaEnumeration$ getScalaEnumeration() {
		ScalaEnumeration$.MODULE$
	}

	JavaEnum$ getJavaEnum() {
		JavaEnum$.MODULE$
	}

	ScalaCaseObjectEnum$ getScalaCaseObjectEnum() {
		ScalaCaseObjectEnum$.MODULE$
	}

	EnumAsScalaString$ getEnumAsScalaString() {
		EnumAsScalaString$.MODULE$
	}

	// union
	OptionalShapelessCoproduct$ getOptionalShapelessCoproduct() {
		OptionalShapelessCoproduct$.MODULE$
	}

	OptionShapelessCoproduct$ getOptionShapelessCoproduct() {
		OptionShapelessCoproduct$.MODULE$
	}

	OptionEitherShapelessCoproduct$ getOptionEitherShapelessCoproduct() {
		OptionEitherShapelessCoproduct$.MODULE$
	}

	// array
	ScalaArray$ getScalaArray() {
		ScalaArray$.MODULE$
	}

	ScalaList$ getScalaList() {
		ScalaList$.MODULE$
	}

	ScalaSeq$ getScalaSeq() {
		ScalaSeq$.MODULE$
	}

	ScalaVector$ getScalaVector() {
		ScalaVector$.MODULE$
	}

	// map
	ScalaMap$ getScalaMap() {
		ScalaMap$.MODULE$
	}

	// protocol
	ScalaADT$ getScalaADT() {
		ScalaADT$.MODULE$
	}

	NoTypeGenerated$ getNoTypeGenerated() {
		NoTypeGenerated$.MODULE$
	}
}

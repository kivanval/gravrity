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

trait ComplexAvroScalaTypes {
	// fixed
	AvroScalaFixedType getScalaCaseClassWrapper() {
		ScalaCaseClassWrapper$.MODULE$
	}

	AvroScalaFixedType getScalaCaseClassWrapperWithSchema() {
		ScalaCaseClassWrapperWithSchema$.MODULE$
	}

	// record
	AvroScalaRecordType getScalaCaseClass() {
		ScalaCaseClass$.MODULE$
	}

	AvroScalaRecordType getScalaCaseClassWithSchema() {
		ScalaCaseClassWithSchema$.MODULE$
	}

	// enum
	AvroScalaEnumType getScalaEnumeration() {
		ScalaEnumeration$.MODULE$
	}

	AvroScalaEnumType getJavaEnum() {
		JavaEnum$.MODULE$
	}

	AvroScalaEnumType getScalaCaseObjectEnum() {
		ScalaCaseObjectEnum$.MODULE$
	}

	AvroScalaEnumType getEnumAsScalaString() {
		EnumAsScalaString$.MODULE$
	}

	// union
	AvroScalaUnionType getOptionalShapelessCoproduct() {
		OptionalShapelessCoproduct$.MODULE$
	}

	AvroScalaUnionType getOptionShapelessCoproduct() {
		OptionShapelessCoproduct$.MODULE$
	}

	AvroScalaUnionType getOptionEitherShapelessCoproduct() {
		OptionEitherShapelessCoproduct$.MODULE$
	}

	// array
	AvroScalaArrayType getScalaArray() {
		ScalaArray$.MODULE$
	}

	AvroScalaArrayType getScalaList() {
		ScalaList$.MODULE$
	}

	AvroScalaArrayType getScalaSeq() {
		ScalaSeq$.MODULE$
	}

	AvroScalaArrayType getScalaVector() {
		ScalaVector$.MODULE$
	}

	// map
	AvroScalaMapType getScalaMap() {
		ScalaMap$.MODULE$
	}

	// protocol
	AvroScalaProtocolType getScalaADT() {
		ScalaADT$.MODULE$
	}

	AvroScalaProtocolType getNoTypeGenerated() {
		NoTypeGenerated$.MODULE$
	}
}

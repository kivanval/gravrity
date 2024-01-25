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

import avrohugger.types.AvroScalaBooleanType
import avrohugger.types.AvroScalaBytesType
import avrohugger.types.AvroScalaNullType
import avrohugger.types.AvroScalaNumberType
import avrohugger.types.AvroScalaStringType
import avrohugger.types.ScalaBoolean$
import avrohugger.types.ScalaByteArray$
import avrohugger.types.ScalaDouble$
import avrohugger.types.ScalaFloat$
import avrohugger.types.ScalaInt$
import avrohugger.types.ScalaLong$
import avrohugger.types.ScalaNull$
import avrohugger.types.ScalaString$

trait PrimitiveAvroScalaTypes {
	AvroScalaNumberType getScalaInt() {
		ScalaInt$.MODULE$
	}

	AvroScalaNumberType getScalaLong(){
		ScalaLong$.MODULE$
	}

	AvroScalaNumberType getScalaFloat(){
		ScalaFloat$.MODULE$
	}

	AvroScalaNumberType getScalaDouble(){
		ScalaDouble$.MODULE$
	}

	AvroScalaBooleanType getScalaBoolean(){
		ScalaBoolean$.MODULE$
	}

	AvroScalaStringType getScalaString(){
		ScalaString$.MODULE$
	}

	AvroScalaNullType getScalaNull(){
		ScalaNull$.MODULE$
	}

	AvroScalaBytesType getScalaByteArray(){
		ScalaByteArray$.MODULE$
	}
}

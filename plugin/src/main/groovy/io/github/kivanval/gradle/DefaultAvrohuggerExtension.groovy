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
import groovy.transform.CompileStatic
import io.github.kivanval.gradle.format.AvroSourceFormat
import io.github.kivanval.gradle.format.SpecificRecord
import io.github.kivanval.gradle.format.Standard
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input

@CompileStatic
class DefaultAvrohuggerExtension implements AvrohuggerExtensionOperations {
	@Input
	Property<AvroSourceFormat> format

	final Provider<AvroSourceFormat> standard
	final Provider<AvroSourceFormat> specificRecord

	final Provider<AvroScalaNumberType> scalaInt
	final Provider<AvroScalaNumberType> scalaLong
	final Provider<AvroScalaNumberType> scalaFloat
	final Provider<AvroScalaNumberType> scalaDouble
	final Provider<AvroScalaBooleanType> scalaBoolean
	final Provider<AvroScalaStringType> scalaString
	final Provider<AvroScalaNullType> scalaNull
	final Provider<AvroScalaBytesType> scalaByteArray

	private final ObjectFactory objects

	@Inject
	DefaultAvrohuggerExtension(ObjectFactory objects) {
		this.objects = objects

		this.format = objects.property(AvroSourceFormat).convention(objects.<Standard>newInstance(Standard))

		this.standard = getProperty(AvroSourceFormat, objects.<Standard>newInstance(Standard))
		this.specificRecord = getProperty(AvroSourceFormat, objects.<SpecificRecord>newInstance(SpecificRecord))

		this.scalaInt = getProperty(AvroScalaNumberType, ScalaInt$.MODULE$)
		this.scalaLong = getProperty(AvroScalaNumberType, ScalaLong$.MODULE$)
		this.scalaFloat = getProperty(AvroScalaNumberType, ScalaFloat$.MODULE$)
		this.scalaDouble = getProperty(AvroScalaNumberType, ScalaDouble$.MODULE$)
		this.scalaBoolean = getProperty(AvroScalaBooleanType, ScalaBoolean$.MODULE$)
		this.scalaString = getProperty(AvroScalaStringType, ScalaString$.MODULE$)
		this.scalaNull = getProperty(AvroScalaNullType, ScalaNull$.MODULE$)
		this.scalaByteArray = getProperty(AvroScalaBytesType, ScalaByteArray$.MODULE$)
	}

	private <T> Property<T> getProperty(Class<T> target, T value) {
		objects.property(target).convention(value)
	}
}

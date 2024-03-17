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
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

import javax.inject.Inject

@CompileStatic
class AvroScalaTypes {
	@Input
	final Property<AvroScalaNumberType> intType
	@Input
	final Property<AvroScalaNumberType> longType
	@Input
	final Property<AvroScalaNumberType> floatType
	@Input
	final Property<AvroScalaNumberType> doubleType
	@Input
	final Property<AvroScalaBooleanType> booleanType
	@Input
	final Property<AvroScalaStringType> stringType
	@Input
	final Property<AvroScalaNullType> nullType
	@Input
	final Property<AvroScalaBytesType> bytesType
	@Input
	final Property<AvroScalaFixedType> fixedType
	@Input
	final Property<AvroScalaRecordType> recordType
	@Input
	final Property<AvroScalaEnumType> enumType
	@Input
	final Property<AvroScalaUnionType> unionType
	@Input
	final Property<AvroScalaArrayType> arrayType
	@Input
	final Property<AvroScalaMapType> mapType
	@Input
	final Property<AvroScalaProtocolType> protocolType
	@Input
	final Property<AvroScalaDecimalType> decimalType
	@Input
	final Property<AvroScalaDateType> dateType
	@Input
	final Property<AvroScalaTimestampMillisType> timestampMillisType
	@Input
	final Property<AvroScalaTimeMillisType> timeMillisType
	@Input
	final Property<AvroScalaTimeType> timeMicrosType
	@Input
	final Property<AvroScalaTimestampType> timestampMicrosType
	@Input
	final Property<AvroScalaLocalTimestampType> localTimestampMillisType
	@Input
	final Property<AvroScalaLocalTimestampType> localTimestampMicrosType
	@Input
	final Property<AvroUuidType> uuidType

	@Inject
	AvroScalaTypes(ObjectFactory objects, avrohugger.types.AvroScalaTypes avroScalaTypes) {
		intType = objects.property(AvroScalaNumberType).convention(avroScalaTypes.int())
		longType = objects.property(AvroScalaNumberType).convention(avroScalaTypes.long())
		floatType = objects.property(AvroScalaNumberType).convention(avroScalaTypes.float())
		doubleType = objects.property(AvroScalaNumberType).convention(avroScalaTypes.double())
		booleanType = objects.property(AvroScalaBooleanType).convention(avroScalaTypes.boolean())
		stringType = objects.property(AvroScalaStringType).convention(avroScalaTypes.string())
		nullType = objects.property(AvroScalaNullType).convention(avroScalaTypes.null())
		bytesType = objects.property(AvroScalaBytesType).convention(avroScalaTypes.bytes())

		fixedType = objects.property(AvroScalaFixedType).convention(avroScalaTypes.fixed())
		recordType = objects.property(AvroScalaRecordType).convention(avroScalaTypes.record())
		enumType = objects.property(AvroScalaEnumType).convention(avroScalaTypes.enum())
		unionType = objects.property(AvroScalaUnionType).convention(avroScalaTypes.union())
		arrayType = objects.property(AvroScalaArrayType).convention(avroScalaTypes.array())
		mapType = objects.property(AvroScalaMapType).convention(avroScalaTypes.map())
		protocolType = objects.property(AvroScalaProtocolType).convention(avroScalaTypes.protocol())

		decimalType = objects.property(AvroScalaDecimalType).convention(avroScalaTypes.decimal())
		dateType = objects.property(AvroScalaDateType).convention(avroScalaTypes.date())
		timestampMillisType = objects.property(AvroScalaTimestampMillisType).convention(avroScalaTypes.timestampMillis())
		timeMillisType = objects.property(AvroScalaTimeMillisType).convention(avroScalaTypes.timeMillis())
		timeMicrosType = objects.property(AvroScalaTimeType).convention(avroScalaTypes.timeMicros())
		timestampMicrosType = objects.property(AvroScalaTimestampType).convention(avroScalaTypes.timestampMicros())
		localTimestampMillisType = objects.property(AvroScalaLocalTimestampType).convention(avroScalaTypes.localTimestampMillis())
		localTimestampMicrosType = objects.property(AvroScalaLocalTimestampType).convention(avroScalaTypes.localTimestampMicros())
		uuidType = objects.property(AvroUuidType).convention(avroScalaTypes.uuid())
	}
}

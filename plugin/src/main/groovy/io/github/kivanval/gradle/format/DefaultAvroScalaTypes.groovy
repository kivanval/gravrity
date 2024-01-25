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
import io.github.kivanval.gradle.format.AvroScalaTypes
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

@CompileStatic
class DefaultAvroScalaTypes implements AvroScalaTypes {
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
	DefaultAvroScalaTypes(ObjectFactory objects) {
		intType = objects.property(AvroScalaNumberType)
		longType = objects.property(AvroScalaNumberType)
		floatType = objects.property(AvroScalaNumberType)
		doubleType = objects.property(AvroScalaNumberType)
		booleanType = objects.property(AvroScalaBooleanType)
		stringType = objects.property(AvroScalaStringType)
		nullType = objects.property(AvroScalaNullType)
		bytesType = objects.property(AvroScalaBytesType)

		fixedType = objects.property(AvroScalaFixedType)
		recordType = objects.property(AvroScalaRecordType)
		enumType = objects.property(AvroScalaEnumType)
		unionType = objects.property(AvroScalaUnionType)
		arrayType = objects.property(AvroScalaArrayType)
		mapType = objects.property(AvroScalaMapType)
		protocolType = objects.property(AvroScalaProtocolType)

		decimalType = objects.property(AvroScalaDecimalType)
		dateType = objects.property(AvroScalaDateType)
		timestampMillisType = objects.property(AvroScalaTimestampMillisType)
		timeMillisType = objects.property(AvroScalaTimeMillisType)
		timeMicrosType = objects.property(AvroScalaTimeType)
		timestampMicrosType = objects.property(AvroScalaTimestampType)
		localTimestampMillisType = objects.property(AvroScalaLocalTimestampType)
		localTimestampMicrosType = objects.property(AvroScalaLocalTimestampType)
		uuidType = objects.property(AvroUuidType)
	}

	AvroScalaTypes initBy(avrohugger.types.AvroScalaTypes avroScalaTypes) {
		intType.convention(avroScalaTypes.int())
		longType.convention(avroScalaTypes.long())
		floatType.convention(avroScalaTypes.float())
		doubleType.convention(avroScalaTypes.double())
		booleanType.convention(avroScalaTypes.boolean())
		stringType.convention(avroScalaTypes.string())
		nullType.convention(avroScalaTypes.null())
		bytesType.convention(avroScalaTypes.bytes())

		fixedType.convention(avroScalaTypes.fixed())
		recordType.convention(avroScalaTypes.record())
		enumType.convention(avroScalaTypes.enum())
		unionType.convention(avroScalaTypes.union())
		arrayType.convention(avroScalaTypes.array())
		mapType.convention(avroScalaTypes.map())
		protocolType.convention(avroScalaTypes.protocol())

		decimalType.convention(avroScalaTypes.decimal())
		dateType.convention(avroScalaTypes.date())
		timestampMillisType.convention(avroScalaTypes.timestampMillis())
		timeMillisType.convention(avroScalaTypes.timeMillis())
		timeMicrosType.convention(avroScalaTypes.timeMicros())
		timestampMicrosType.convention(avroScalaTypes.timestampMicros())
		localTimestampMillisType.convention(avroScalaTypes.localTimestampMillis())
		localTimestampMicrosType.convention(avroScalaTypes.localTimestampMicros())
		uuidType.convention(avroScalaTypes.uuid())

		this
	}
}

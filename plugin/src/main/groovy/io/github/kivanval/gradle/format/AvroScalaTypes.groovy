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
import org.gradle.api.provider.Property

interface AvroScalaTypes {

	// primitive

	Property<AvroScalaNumberType> getIntType()

	Property<AvroScalaNumberType> getLongType()

	Property<AvroScalaNumberType> getFloatType()

	Property<AvroScalaNumberType> getDoubleType()

	Property<AvroScalaBooleanType> getBooleanType()

	Property<AvroScalaStringType> getStringType()

	Property<AvroScalaNullType> getNullType()

	Property<AvroScalaBytesType> getBytesType()

	// complex

	Property<AvroScalaFixedType> getFixedType()

	Property<AvroScalaRecordType> getRecordType()

	Property<AvroScalaEnumType> getEnumType()

	Property<AvroScalaUnionType> getUnionType()

	Property<AvroScalaArrayType> getArrayType()

	Property<AvroScalaMapType> getMapType()

	Property<AvroScalaProtocolType> getProtocolType()

	// logical

	Property<AvroScalaDecimalType> getDecimalType()

	Property<AvroScalaDateType> getDateType()

	Property<AvroScalaTimestampMillisType> getTimestampMillisType()

	Property<AvroScalaTimeMillisType> getTimeMillisType()

	Property<AvroScalaTimeType> getTimeMicrosType()

	Property<AvroScalaTimestampType> getTimestampMicrosType()

	Property<AvroScalaLocalTimestampType> getLocalTimestampMillisType()

	Property<AvroScalaLocalTimestampType> getLocalTimestampMicrosType()

	Property<AvroUuidType> getUuidType()
}

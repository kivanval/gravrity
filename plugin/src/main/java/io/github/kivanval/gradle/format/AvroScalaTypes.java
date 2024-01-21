package io.github.kivanval.gradle.format;

import avrohugger.types.*;
import org.gradle.api.provider.Property;

public interface AvroScalaTypes {

	// primitive

	Property<AvroScalaNumberType> getIntType();

	Property<AvroScalaNumberType> getLongType();

	Property<AvroScalaNumberType> getFloatType();

	Property<AvroScalaNumberType> getDoubleType();

	Property<AvroScalaBooleanType> getBooleanType();

	Property<AvroScalaStringType> getStringType();

	Property<AvroScalaNullType> getNullType();

	Property<AvroScalaBytesType> getBytesType();

	// complex

	Property<AvroScalaFixedType> getFixedType();

	Property<AvroScalaRecordType> getRecordType();

	Property<AvroScalaEnumType> getEnumType();

	Property<AvroScalaUnionType> getUnionType();

	Property<AvroScalaArrayType> getArrayType();

	Property<AvroScalaMapType> getMapType();

	Property<AvroScalaProtocolType> getProtocolType();

	// logical

	Property<AvroScalaDecimalType> getDecimalType();

	Property<AvroScalaDateType> getDateType();

	Property<AvroScalaTimestampMillisType> getTimestampMillisType();

	Property<AvroScalaTimeMillisType> getTimeMillisType();

	Property<AvroScalaTimeType> getTimeMicrosType();

	Property<AvroScalaTimestampType> getTimestampMicrosType();

	Property<AvroScalaLocalTimestampType> getLocalTimestampMillisType();

	Property<AvroScalaLocalTimestampType> getLocalTimestampMicrosType();

	Property<AvroUuidType> getUuidType();
}

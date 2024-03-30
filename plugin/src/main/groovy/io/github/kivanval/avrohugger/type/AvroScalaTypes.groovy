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
package io.github.kivanval.avrohugger.type

import avrohugger.types.*
import avrohugger.types.AvroScalaTypes as AvrohuggerAvroScalaTypes
import groovy.transform.CompileStatic
import javax.inject.Inject

@CompileStatic
class AvroScalaTypes implements Serializable {
  AvroScalaNumberType intType
  AvroScalaNumberType longType
  AvroScalaNumberType floatType
  AvroScalaNumberType doubleType
  AvroScalaBooleanType booleanType
  AvroScalaStringType stringType
  AvroScalaNullType nullType
  AvroScalaBytesType bytesType
  AvroScalaFixedType fixedType
  AvroScalaRecordType recordType
  AvroScalaEnumType enumType
  AvroScalaUnionType unionType
  AvroScalaArrayType arrayType
  AvroScalaMapType mapType
  AvroScalaProtocolType protocolType
  AvroScalaDecimalType decimalType
  AvroScalaDateType dateType
  AvroScalaTimestampMillisType timestampMillisType
  AvroScalaTimeMillisType timeMillisType
  AvroScalaTimeType timeMicrosType
  AvroScalaTimestampType timestampMicrosType
  AvroScalaLocalTimestampType localTimestampMillisType
  AvroScalaLocalTimestampType localTimestampMicrosType
  AvroUuidType uuidType

  @Inject
  AvroScalaTypes(AvrohuggerAvroScalaTypes avroScalaTypes) {
    intType = avroScalaTypes.int()
    longType = avroScalaTypes.long()
    floatType = avroScalaTypes.float()
    doubleType = avroScalaTypes.double()
    booleanType = avroScalaTypes.boolean()
    stringType = avroScalaTypes.string()
    nullType = avroScalaTypes.null()
    bytesType = avroScalaTypes.bytes()

    fixedType = avroScalaTypes.fixed()
    recordType = avroScalaTypes.record()
    enumType = avroScalaTypes.enum()
    unionType = avroScalaTypes.union()
    arrayType = avroScalaTypes.array()
    mapType = avroScalaTypes.map()
    protocolType = avroScalaTypes.protocol()

    decimalType = avroScalaTypes.decimal()
    dateType = avroScalaTypes.date()
    timestampMillisType = avroScalaTypes.timestampMillis()
    timeMillisType = avroScalaTypes.timeMillis()
    timeMicrosType = avroScalaTypes.timeMicros()
    timestampMicrosType = avroScalaTypes.timestampMicros()
    localTimestampMillisType = avroScalaTypes.localTimestampMillis()
    localTimestampMicrosType = avroScalaTypes.localTimestampMicros()
    uuidType = avroScalaTypes.uuid()
  }

  AvrohuggerAvroScalaTypes getOrigin() {
    new AvrohuggerAvroScalaTypes(
      // primitive
      intType,
      longType,
      floatType,
      doubleType,
      booleanType,
      stringType,
      nullType,
      bytesType,
      // complex
      fixedType,
      recordType,
      enumType,
      unionType,
      arrayType,
      mapType,
      protocolType,
      // logical
      decimalType,
      dateType,
      timestampMillisType,
      timeMillisType,
      timeMicrosType,
      timestampMicrosType,
      localTimestampMillisType,
      localTimestampMicrosType,
      uuidType
      )
  }
}

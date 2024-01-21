package io.github.kivanval.gradle.format;

import avrohugger.types.*;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import scala.Option;

@Getter(onMethod_ = {@Input})
public class DefaultAvroScalaTypes implements AvroScalaTypes {
  private final Property<AvroScalaNumberType> intType;
  private final Property<AvroScalaNumberType> longType;
  private final Property<AvroScalaNumberType> floatType;
  private final Property<AvroScalaNumberType> doubleType;
  private final Property<AvroScalaBooleanType> booleanType;
  private final Property<AvroScalaStringType> stringType;
  private final Property<AvroScalaNullType> nullType;
  private final Property<AvroScalaBytesType> bytesType;
  private final Property<AvroScalaFixedType> fixedType;
  private final Property<AvroScalaRecordType> recordType;
  private final Property<AvroScalaEnumType> enumType;
  private final Property<AvroScalaUnionType> unionType;
  private final Property<AvroScalaArrayType> arrayType;
  private final Property<AvroScalaMapType> mapType;
  private final Property<AvroScalaProtocolType> protocolType;
  private final Property<AvroScalaDecimalType> decimalType;
  private final Property<AvroScalaDateType> dateType;
  private final Property<AvroScalaTimestampMillisType> timestampMillisType;
  private final Property<AvroScalaTimeMillisType> timeMillisType;
  private final Property<AvroScalaTimeType> timeMicrosType;
  private final Property<AvroScalaTimestampType> timestampMicrosType;
  private final Property<AvroScalaLocalTimestampType> localTimestampMillisType;
  private final Property<AvroScalaLocalTimestampType> localTimestampMicrosType;
  private final Property<AvroUuidType> uuidType;

  @Getter(AccessLevel.NONE)
  ObjectFactory objects;

  @Inject
  public DefaultAvroScalaTypes(ObjectFactory objectFactory) {
    this.objects = objectFactory;
    this.intType = getProperty(AvroScalaNumberType.class, ScalaInt$.MODULE$);
    this.longType = getProperty(AvroScalaNumberType.class, ScalaLong$.MODULE$);
    this.floatType = getProperty(AvroScalaNumberType.class, ScalaFloat$.MODULE$);
    this.doubleType = getProperty(AvroScalaNumberType.class, ScalaDouble$.MODULE$);
    this.booleanType = getProperty(AvroScalaBooleanType.class, ScalaBoolean$.MODULE$);
    this.stringType = getProperty(AvroScalaStringType.class, ScalaString$.MODULE$);
    this.nullType = getProperty(AvroScalaNullType.class, ScalaNull$.MODULE$);
    this.bytesType = getProperty(AvroScalaBytesType.class, ScalaByteArray$.MODULE$);

    this.fixedType = getProperty(AvroScalaFixedType.class, ScalaCaseClassWrapper$.MODULE$);
    this.recordType = getProperty(AvroScalaRecordType.class, ScalaCaseClass$.MODULE$);
    this.enumType = getProperty(AvroScalaEnumType.class, ScalaEnumeration$.MODULE$);
    this.unionType = getProperty(AvroScalaUnionType.class, OptionEitherShapelessCoproduct$.MODULE$);
    this.arrayType = getProperty(AvroScalaArrayType.class, ScalaSeq$.MODULE$);
    this.mapType = getProperty(AvroScalaMapType.class, ScalaMap$.MODULE$);
    this.protocolType = getProperty(AvroScalaProtocolType.class, NoTypeGenerated$.MODULE$);

    this.decimalType = getProperty(AvroScalaDecimalType.class, new ScalaBigDecimal(Option.empty()));
    this.dateType = getProperty(AvroScalaDateType.class, JavaTimeLocalDate$.MODULE$);
    this.timestampMillisType =
        getProperty(AvroScalaTimestampMillisType.class, JavaTimeInstant$.MODULE$);
    this.timeMillisType = getProperty(AvroScalaTimeMillisType.class, JavaTimeLocalTime$.MODULE$);
    this.timeMicrosType = getProperty(AvroScalaTimeType.class, JavaTimeLocalTime$.MODULE$);
    this.timestampMicrosType =
        getProperty(AvroScalaTimestampType.class, JavaTimeZonedDateTime$.MODULE$);
    this.localTimestampMillisType =
        getProperty(AvroScalaLocalTimestampType.class, JavaTimeLocalDateTime$.MODULE$);
    this.localTimestampMicrosType =
        getProperty(AvroScalaLocalTimestampType.class, JavaTimeLocalDateTime$.MODULE$);
    this.uuidType = getProperty(AvroUuidType.class, JavaUuid$.MODULE$);
  }

  private <T> Property<T> getProperty(Class<T> target, T value) {
    return objects.property(target).convention(value);
  }
}
package io.github.kivanval.gradle.format;

import org.gradle.api.provider.Property;

public interface AvroScalaTypes {
  Property<AvroScalaNumberType> getIntType();

  Property<AvroScalaNumberType> getLongType();

  Property<AvroScalaNumberType> getFloatType();

  Property<AvroScalaNumberType> getDoubleType();
}

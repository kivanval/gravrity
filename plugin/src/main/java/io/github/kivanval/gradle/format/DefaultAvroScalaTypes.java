package io.github.kivanval.gradle.format;

import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public class DefaultAvroScalaTypes implements AvroScalaTypes {
  private final Property<AvroScalaNumberType> intType;
  private final Property<AvroScalaNumberType> longType;
  private final Property<AvroScalaNumberType> floatType;
  private final Property<AvroScalaNumberType> doubleType;

  @Inject
  public DefaultAvroScalaTypes(ObjectFactory objects) {
    this.intType =
        objects.property(AvroScalaNumberType.class).convention(AvroScalaNumberType.SCALA_INT);
    this.longType =
        objects.property(AvroScalaNumberType.class).convention(AvroScalaNumberType.SCALA_LONG);
    this.floatType =
        objects.property(AvroScalaNumberType.class).convention(AvroScalaNumberType.SCALA_FLOAT);
    this.doubleType =
        objects.property(AvroScalaNumberType.class).convention(AvroScalaNumberType.SCALA_DOUBLE);
  }
}

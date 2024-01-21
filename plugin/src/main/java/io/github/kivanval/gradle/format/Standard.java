package io.github.kivanval.gradle.format;

import avrohugger.format.Standard$;
import avrohugger.format.abstractions.SourceFormat;
import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public class Standard implements AvroSourceFormat {
  private final Provider<SourceFormat> sourceFormat;
  private final Provider<AvroScalaTypes> defaultTypes;
  private final Property<AvroScalaTypes> types;

  @Inject
  public Standard(ObjectFactory objects) {
    this.sourceFormat = objects.property(SourceFormat.class).value(Standard$.MODULE$);
    this.defaultTypes = defaultTypesProperty(objects);
    this.types = defaultTypesProperty(objects);
  }

  static Property<AvroScalaTypes> defaultTypesProperty(ObjectFactory objects) {
    return objects
        .property(AvroScalaTypes.class)
        .value(objects.newInstance(DefaultAvroScalaTypes.class));
  }
}

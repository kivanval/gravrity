package io.github.kivanval.gradle;

import io.github.kivanval.gradle.format.AvroSourceFormat;
import io.github.kivanval.gradle.format.Standard;
import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public abstract class DefaultAvrohuggerExtension implements AvrohuggerExtension {
  private final Property<AvroSourceFormat> format;

  @Inject
  public DefaultAvrohuggerExtension(ObjectFactory objects) {
    this.format =
        objects.property(AvroSourceFormat.class).convention(objects.newInstance(Standard.class));
  }
}

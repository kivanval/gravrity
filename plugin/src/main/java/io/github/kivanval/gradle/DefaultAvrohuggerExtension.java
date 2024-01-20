package io.github.kivanval.gradle;

import io.github.kivanval.gradle.format.SourceFormat;
import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public abstract class DefaultAvrohuggerExtension implements AvrohuggerExtension {
  private final Property<SourceFormat> format;

  @Inject
  public DefaultAvrohuggerExtension(ObjectFactory objects) {
    this.format = objects.property(SourceFormat.class).convention(SourceFormat.STANDARD);
  }
}

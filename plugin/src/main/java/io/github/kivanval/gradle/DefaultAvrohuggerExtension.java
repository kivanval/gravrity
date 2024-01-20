package io.github.kivanval.gradle;

import io.github.kivanval.gradle.format.DefaultFormatSettingsContainer;
import io.github.kivanval.gradle.format.FormatSettingsContainer;
import io.github.kivanval.gradle.source.AvroSourceSetContainer;
import io.github.kivanval.gradle.source.DefaultAvroSourceSetContainer;
import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public class DefaultAvrohuggerExtension implements AvrohuggerExtension {
  private final AvroSourceSetContainer sourceSets;
  private final FormatSettingsContainer formatSettings;

  @Inject
  public DefaultAvrohuggerExtension(final ObjectFactory objects) {
    this.sourceSets = objects.newInstance(DefaultAvroSourceSetContainer.class);
    this.formatSettings = objects.newInstance(DefaultFormatSettingsContainer.class);
  }
}

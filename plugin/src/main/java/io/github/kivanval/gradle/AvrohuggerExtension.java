package io.github.kivanval.gradle;

import io.github.kivanval.gradle.format.FormatSettingsContainer;
import io.github.kivanval.gradle.source.AvroSourceSetContainer;
import org.gradle.api.Incubating;

@Incubating
public interface AvrohuggerExtension {
  FormatSettingsContainer getFormatSettings();

  AvroSourceSetContainer getSourceSets();
}

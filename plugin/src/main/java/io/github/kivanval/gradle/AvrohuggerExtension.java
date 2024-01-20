package io.github.kivanval.gradle;

import io.github.kivanval.gradle.format.SourceFormat;
import org.gradle.api.Incubating;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

@Incubating
public interface AvrohuggerExtension extends ExtensionAware {
  @Input
  Property<SourceFormat> getFormat();
}

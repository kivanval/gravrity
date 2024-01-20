package io.github.kivanval.gradle.format;

import org.gradle.api.Describable;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.reflect.HasPublicType;

public interface FormatSettings extends ExtensionAware, HasPublicType, Describable {
  String getName();
}

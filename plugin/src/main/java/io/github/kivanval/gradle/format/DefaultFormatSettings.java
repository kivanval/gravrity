package io.github.kivanval.gradle.format;

import static org.gradle.api.reflect.TypeOf.typeOf;

import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.reflect.TypeOf;
import org.gradle.api.tasks.SourceSet;
import org.gradle.util.internal.GUtil;

@Getter
public abstract class DefaultFormatSettings implements FormatSettings {
  private final String name;
  private final String baseName;
  private final String displayName;

  @Inject
  public DefaultFormatSettings(final String name, final ObjectFactory objects) {
    this.name = name;
    this.baseName = name.equals(SourceSet.MAIN_SOURCE_SET_NAME) ? "" : GUtil.toCamelCase(name);
    this.displayName = GUtil.toWords(name);
  }

  @Override
  public TypeOf<FormatSettings> getPublicType() {
    return typeOf(FormatSettings.class);
  }
}

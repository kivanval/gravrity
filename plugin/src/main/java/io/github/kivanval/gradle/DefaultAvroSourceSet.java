package io.github.kivanval.gradle;

import groovy.lang.Closure;
import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.Action;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.reflect.HasPublicType;
import org.gradle.api.reflect.TypeOf;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;
import org.gradle.util.internal.GUtil;

import static org.gradle.util.internal.ConfigureUtil.configure;

@Getter
public abstract class DefaultAvroSourceSet implements AvroSourceSet, HasPublicType {
  @Getter(onMethod_ = {@Input})
  private final SourceDirectorySet avro;

  private final String name;
  private final String baseName;
  private final String displayName;

  @Inject
  public DefaultAvroSourceSet(final String name, final ObjectFactory objects) {
    this.name = name;
    this.baseName = name.equals(SourceSet.MAIN_SOURCE_SET_NAME) ? "" : GUtil.toCamelCase(name);
    this.displayName = GUtil.toWords(name);
    this.avro = objects.sourceDirectorySet("avro", displayName + " Avro source");
    this.avro.getFilter().include("**/*.avro", "**/*.avsc", "**/*.avpr", "**/*.avdl");
  }

  @Override
  public AvroSourceSet avro(Closure<? super SourceDirectorySet> configureClosure) {
    configure(configureClosure, getAvro());
    return this;
  }

  @Override
  public AvroSourceSet avro(Action<? super SourceDirectorySet> configureAction) {
    configureAction.execute(getAvro());
    return this;
  }

  @Override
  public TypeOf<AvroSourceSet> getPublicType() {
    return TypeOf.typeOf(AvroSourceSet.class);
  }
}

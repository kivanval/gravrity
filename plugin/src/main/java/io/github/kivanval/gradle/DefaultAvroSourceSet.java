package io.github.kivanval.gradle;

import static org.gradle.util.ConfigureUtil.configure;

import groovy.lang.Closure;
import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.Action;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.reflect.HasPublicType;
import org.gradle.api.reflect.TypeOf;

@Getter
public abstract class DefaultAvroSourceSet implements AvroSourceSet, HasPublicType {
  private final SourceDirectorySet avro;

  @Inject
  public DefaultAvroSourceSet(String displayName, ObjectFactory objects) {
    avro = objects.sourceDirectorySet("avro", displayName + " Avro source");
    avro.getFilter().include("**/*.avro", "**/*.avsc", "**/*.avpr", "**/*.avdl");
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

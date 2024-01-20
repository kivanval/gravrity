package io.github.kivanval.gradle.source;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.Describable;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.reflect.HasPublicType;

public interface AvroSourceSet extends ExtensionAware, HasPublicType, Describable {
  String getName();

  SourceDirectorySet getAvro();

  AvroSourceSet avro(Closure<? super SourceDirectorySet> configureClosure);

  AvroSourceSet avro(Action<? super SourceDirectorySet> configureAction);
}

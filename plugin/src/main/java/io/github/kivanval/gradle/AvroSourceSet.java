package io.github.kivanval.gradle;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import org.gradle.api.Action;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.ExtensionAware;

public interface AvroSourceSet extends ExtensionAware {
  SourceDirectorySet getAvro();

  AvroSourceSet avro(
      @DelegatesTo(SourceDirectorySet.class) Closure<? super SourceDirectorySet> configureClosure);

  AvroSourceSet avro(Action<? super SourceDirectorySet> configureAction);
}

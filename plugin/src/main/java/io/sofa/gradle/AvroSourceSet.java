package io.sofa.gradle;

import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.file.SourceDirectorySet;

public interface AvroSourceSet {
  SourceDirectorySet getAvro();

  AvroSourceSet avro(Closure<AvroSourceSet> configureClosure);

  AvroSourceSet avro(Action<? super SourceDirectorySet> configureAction);
}

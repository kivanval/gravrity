package io.github.kivanval.gradle.format;

import static org.gradle.util.internal.ConfigureUtil.configure;

import avrohugger.format.abstractions.SourceFormat;
import groovy.lang.Closure;
import org.gradle.api.Action;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;

public interface AvroSourceFormat {
  Provider<SourceFormat> getSourceFormat();

  Property<AvroScalaTypes> getTypes();

  Provider<AvroScalaTypes> getDefaultTypes();

  default Provider<AvroScalaTypes> defaultTypes(Closure<? super AvroScalaTypes> configureClosure) {
    return getDefaultTypes().map(avroScalaTypes -> configure(configureClosure, avroScalaTypes));
  }

  default Provider<AvroScalaTypes> defaultTypes(Action<? super AvroScalaTypes> configureAction) {
    return getDefaultTypes()
        .map(
            avroScalaTypes -> {
              configureAction.execute(avroScalaTypes);
              return avroScalaTypes;
            });
  }
}

package io.github.kivanval.gradle;

import org.gradle.api.Incubating;
import org.gradle.api.NamedDomainObjectContainer;

@Incubating
public interface AvrohuggerExtension {
  NamedDomainObjectContainer<AvroSourceSet> getNamedObjectContainer();
}

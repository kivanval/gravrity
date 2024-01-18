package io.sofa.gradle;

import javax.inject.Inject;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;

public class DefaultAvrohuggerExtension implements AvrohuggerExtension {

  private final NamedDomainObjectContainer<AvroSourceSet> sourceSetContainer;

  @Inject
  public DefaultAvrohuggerExtension(
      AvroSourceSet avroSourceSet, final ObjectFactory objectFactory) {
    this.sourceSetContainer =
        objectFactory.domainObjectContainer(
            AvroSourceSet.class, name -> new DefaultAvroSourceSet(name, objectFactory));
    sourceSetContainer.create("avro");
  }

  public NamedDomainObjectContainer<AvroSourceSet> getNamedObjectContainer() {
    return this.sourceSetContainer;
  }
}

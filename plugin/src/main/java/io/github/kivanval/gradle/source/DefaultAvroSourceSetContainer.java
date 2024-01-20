package io.github.kivanval.gradle.source;

import static org.gradle.api.reflect.TypeOf.typeOf;

import javax.inject.Inject;
import org.gradle.api.internal.AbstractValidatingNamedDomainObjectContainer;
import org.gradle.api.internal.CollectionCallbackActionDecorator;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.reflect.TypeOf;
import org.gradle.internal.reflect.Instantiator;

public class DefaultAvroSourceSetContainer
    extends AbstractValidatingNamedDomainObjectContainer<AvroSourceSet>
    implements AvroSourceSetContainer {
  private final ObjectFactory objects;

  @Inject
  public DefaultAvroSourceSetContainer(
      Instantiator instantiator,
      ObjectFactory objects,
      CollectionCallbackActionDecorator collectionCallbackActionDecorator) {
    super(
        AvroSourceSet.class,
        instantiator,
        AvroSourceSet::getName,
        collectionCallbackActionDecorator);
    this.objects = objects;
  }

  @Override
  protected AvroSourceSet doCreate(String name) {
    return getInstantiator().newInstance(DefaultAvroSourceSet.class, name, objects);
  }

  @Override
  public TypeOf<AvroSourceSetContainer> getPublicType() {
    return typeOf(AvroSourceSetContainer.class);
  }
}

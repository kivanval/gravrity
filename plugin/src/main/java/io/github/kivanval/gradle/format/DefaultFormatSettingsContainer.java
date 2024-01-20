package io.github.kivanval.gradle.format;

import static org.gradle.api.reflect.TypeOf.typeOf;

import javax.inject.Inject;
import org.gradle.api.internal.AbstractValidatingNamedDomainObjectContainer;
import org.gradle.api.internal.CollectionCallbackActionDecorator;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.reflect.TypeOf;
import org.gradle.internal.reflect.Instantiator;

public class DefaultFormatSettingsContainer
    extends AbstractValidatingNamedDomainObjectContainer<FormatSettings>
    implements FormatSettingsContainer {
  private final ObjectFactory objects;

  @Inject
  public DefaultFormatSettingsContainer(
      Instantiator instantiator,
      ObjectFactory objects,
      CollectionCallbackActionDecorator collectionCallbackActionDecorator) {
    super(
        FormatSettings.class,
        instantiator,
        FormatSettings::getName,
        collectionCallbackActionDecorator);
    this.objects = objects;
  }

  @Override
  protected FormatSettings doCreate(String name) {
    return getInstantiator().newInstance(DefaultFormatSettings.class, name, objects);
  }

  @Override
  public TypeOf<FormatSettingsContainer> getPublicType() {
    return typeOf(FormatSettingsContainer.class);
  }
}

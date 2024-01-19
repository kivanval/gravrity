package io.github.kivanval.gradle;

import javax.inject.Inject;
import lombok.Getter;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.SourceSet;

public class DefaultAvrohuggerExtension implements AvrohuggerExtension {
  @Getter(onMethod_ = {@Input})
  private final NamedDomainObjectContainer<AvroSourceSet> sourceSets;

  @Inject
  public DefaultAvrohuggerExtension(final Project project, final ObjectFactory objects) {
    this.sourceSets =
        objects.domainObjectContainer(
            AvroSourceSet.class,
            name -> objects.newInstance(DefaultAvroSourceSet.class, name, objects));
    sourceSets.create(SourceSet.MAIN_SOURCE_SET_NAME);
    sourceSets.create(SourceSet.TEST_SOURCE_SET_NAME);
  }
}

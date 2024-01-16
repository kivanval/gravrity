package io.sofa.gradle;

import java.util.Objects;
import lombok.experimental.ExtensionMethod;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSetContainer;

@ExtensionMethod(Objects.class)
public class AvrohuggerBasePlugin implements Plugin<Project> {
  @Override
  public void apply(Project project) {
    project
        .getExtensions()
        .configure(
            SourceSetContainer.class,
            sourceSets ->
                sourceSets.configureEach(
                    sourceSet -> sourceSet.getExtensions().create("avro", AvroSourceSet.class)));
  }
}

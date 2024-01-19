package io.github.kivanval.gradle;

import java.util.Objects;
import javax.inject.Inject;
import lombok.experimental.ExtensionMethod;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.internal.file.DefaultSourceDirectorySet;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.scala.ScalaBasePlugin;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.internal.Cast;

@ExtensionMethod(Objects.class)
public class AvrohuggerBasePlugin implements Plugin<Project> {
  final ObjectFactory objects;

  @Inject
  public AvrohuggerBasePlugin(ObjectFactory objects) {
    this.objects = objects;
  }

  @Override
  public void apply(Project project) {
    project.getPluginManager().apply(ScalaBasePlugin.class);

    configureSourceSetDefaults(project, objects);
  }

  private static void configureSourceSetDefaults(
      final Project project, final ObjectFactory objects) {

    final var sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
    sourceSets.all(
        sourceSet -> {
          final var convention = (Convention) InvokerHelper.getProperty(sourceSet, "convention");
          final var avroSourceSet =
              objects.newInstance(DefaultAvroSourceSet.class, sourceSet.getName(), objects);
          convention.getPlugins().put("avro", avroSourceSet);

          final var avroDirectorySet =
              Cast.cast(DefaultSourceDirectorySet.class, avroSourceSet.getAvro());
          final var suffix = sourceSet.getName() + "/avro";
          avroDirectorySet.srcDir("src/" + suffix);

          sourceSet.getAllSource().source(avroDirectorySet);
          sourceSet.getResources().source(avroDirectorySet);
        });

    addExtensions(project, objects);
  }

  private static void addExtensions(final Project project, final ObjectFactory objects) {
    project
        .getExtensions()
        .create(
            AvrohuggerExtension.class,
            "avrohugger",
            DefaultAvrohuggerExtension.class,
            project,
            objects);
  }
}

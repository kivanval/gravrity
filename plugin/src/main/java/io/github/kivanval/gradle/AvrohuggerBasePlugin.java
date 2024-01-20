package io.github.kivanval.gradle;

import java.util.Objects;
import javax.inject.Inject;
import lombok.experimental.ExtensionMethod;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.tasks.DefaultSourceSetOutput;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.scala.ScalaBasePlugin;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.internal.Cast;
import org.gradle.util.internal.GUtil;

@ExtensionMethod(Objects.class)
public class AvrohuggerBasePlugin implements Plugin<Project> {
  private final ObjectFactory objects;

  @Inject
  public AvrohuggerBasePlugin(ObjectFactory objects) {
    this.objects = objects;
  }

  @Override
  public void apply(Project project) {
    project.getPluginManager().apply(ScalaBasePlugin.class);

    configureSourceSetDefaults(project, objects);
    configureExtension(project);
  }

  private static void configureSourceSetDefaults(
      final Project project, final ObjectFactory objects) {

    final var sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
    sourceSets.all(
        sourceSet -> {
          final var displayName = GUtil.toWords(sourceSet.getName()) + " Avro source";
          final var avro = objects.sourceDirectorySet("avro", displayName);
          sourceSet.getExtensions().add(SourceDirectorySet.class, "avro", avro);
          avro.srcDir("src/" + sourceSet.getName() + "/" + avro.getName());

          sourceSet.getAllSource().source(avro);
          sourceSet.getResources().source(avro);

          // TODO Maybe, move in task creating step
          final var output = Cast.cast(DefaultSourceSetOutput.class, sourceSet.getOutput());
          final var avroScalaGeneratedPath =
              "generated/sources/avrohugger/scala/" + sourceSet.getName();
          output
              .getGeneratedSourcesDirs()
              .from(project.getLayout().getBuildDirectory().dir(avroScalaGeneratedPath));
        });
  }

  private static final String AVROHUGGER_EXTENSION_NAME = "avrohugger";

  private static void configureExtension(final Project project) {
    project
        .getExtensions()
        .create(
            AvrohuggerExtension.class, AVROHUGGER_EXTENSION_NAME, DefaultAvrohuggerExtension.class);
  }
}

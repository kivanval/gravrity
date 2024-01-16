package io.sofa.gradle;

import java.util.Objects;
import javax.inject.Inject;
import lombok.experimental.ExtensionMethod;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.scala.ScalaBasePlugin;

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
    project
        .getConvention()
        .findPlugin(JavaPluginConvention.class)
        .getSourceSets()
        .all(
            sourceSet -> {
              var displayName =
                  (String) InvokerHelper.invokeMethod(sourceSet, "getDisplayName", null);
              var convention = (Convention) InvokerHelper.getProperty(sourceSet, "convention");
              convention.getPlugins().put("avro", new DefaultAvroSourceSet(displayName, objects));
            });
  }
}

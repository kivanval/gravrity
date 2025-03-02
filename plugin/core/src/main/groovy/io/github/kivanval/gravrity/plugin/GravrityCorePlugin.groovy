/*
Copyright 2024 Ivan Kyrylov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package io.github.kivanval.gravrity.plugin

import groovy.transform.CompileStatic
import javax.inject.Inject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

@CompileStatic
class GravrityCorePlugin implements Plugin<Project> {
  private final Project project

  @Inject
  GravrityCorePlugin(Project project) {
    this.project = project
  }

  @Override
  void apply(final Project project) {
    project.pluginManager.with {
      it.apply(GravrityCoreBasePlugin)
      it.apply(JavaPlugin)
    }
  }
}

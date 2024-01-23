package io.github.kivanval.gradle

import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project

@CompileStatic
class AvrohuggerPlugin implements Plugin<Project> {
	@Override
	void apply(final Project project) {
		project.pluginManager.apply(AvrohuggerBasePlugin)
		// TODO
	}
}

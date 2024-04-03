package io.github.kivanval.gradle.util

import org.gradle.testkit.runner.GradleRunner

class TestUtils {

    static final List<String> GRADLE_VERSIONS = ["7.2", "7.4.2", "8.5"]

    static GradleRunner gradleRunner(
            File projectDir,
            String gradleVersion,
            String... arguments
    ) {
        GradleRunner.create()
                .forwardOutput()
                .withPluginClasspath()
                .withArguments(arguments)
                .withProjectDir(projectDir)
                .withGradleVersion(gradleVersion)
    }

}

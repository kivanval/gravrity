package io.github.kivanval.gradle

import java.nio.file.Files
import java.nio.file.Paths
import org.gradle.internal.impldep.com.google.common.io.Resources

class ResourceHelper {
	static String read(String resourceName) {
		Files.readString(Paths.get(Resources.getResource(resourceName).toURI()))
	}
}

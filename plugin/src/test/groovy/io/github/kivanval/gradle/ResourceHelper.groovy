package io.github.kivanval.gradle

import java.nio.file.Files
import java.nio.file.Paths
import lombok.experimental.UtilityClass
import org.gradle.internal.impldep.com.google.common.io.Resources

@UtilityClass
class ResourceHelper {
	static String read(String resourceName) {
		return Files.readString(Paths.get(Resources.getResource(resourceName).toURI()))
	}
}

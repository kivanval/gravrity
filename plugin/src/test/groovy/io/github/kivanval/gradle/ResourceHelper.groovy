/*
Copyright 2024 Kyrylov Ivan

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

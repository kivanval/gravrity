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

import avrohugger.types.*
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class AvrohuggerTypesTest extends Specification {
	def "checking reference for primitive types"() {
		given:
		def project = ProjectBuilder.builder().build()

		when:
		project.pluginManager.with {
			apply(AvrohuggerPlugin)
		}

		then:
		project.avrohugger.scalaInt == ScalaInt$.MODULE$
		project.avrohugger.scalaLong == ScalaLong$.MODULE$
		project.avrohugger.scalaFloat == ScalaFloat$.MODULE$
		project.avrohugger.scalaDouble == ScalaDouble$.MODULE$
		project.avrohugger.scalaBoolean == ScalaBoolean$.MODULE$
		project.avrohugger.scalaString == ScalaString$.MODULE$
		project.avrohugger.scalaNull == ScalaNull$.MODULE$
		project.avrohugger.scalaByteArray == ScalaByteArray$.MODULE$
	}
}

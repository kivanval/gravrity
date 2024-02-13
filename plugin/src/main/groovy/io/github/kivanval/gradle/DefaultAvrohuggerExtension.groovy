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

import groovy.transform.CompileStatic
import io.github.kivanval.gradle.format.AvroSourceFormat
import io.github.kivanval.gradle.format.SpecificRecord
import io.github.kivanval.gradle.format.Standard
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

@CompileStatic
class DefaultAvrohuggerExtension implements AvrohuggerExtension {
	private final ObjectFactory objects

	@Input
	final Property<AvroSourceFormat> format

	@Input
	final MapProperty<String, String> namespaceMapping

	@Input
	final Property<Boolean> restrictedFieldNumber

	@Inject
	DefaultAvrohuggerExtension(ObjectFactory objects) {
		this.objects = objects

		this.format = objects.property(AvroSourceFormat).convention(objects.<Standard> newInstance(Standard))

		this.namespaceMapping = objects.mapProperty(String, String)

		this.restrictedFieldNumber = objects.property(Boolean).convention(false)
	}

	@Override
	AvroSourceFormat getStandard() {
		return objects.<Standard> newInstance(Standard)
	}

	@Override
	AvroSourceFormat getSpecificRecord() {
		return objects.<SpecificRecord> newInstance(SpecificRecord)
	}
}

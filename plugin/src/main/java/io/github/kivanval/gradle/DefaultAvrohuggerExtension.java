/*
* Copyright 2024 Kyrylov Ivan
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package io.github.kivanval.gradle;

import avrohugger.types.AvroScalaNumberType;
import avrohugger.types.ScalaInt$;
import io.github.kivanval.gradle.format.AvroSourceFormat;
import io.github.kivanval.gradle.format.SpecificRecord;
import io.github.kivanval.gradle.format.Standard;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public class DefaultAvrohuggerExtension implements AvrohuggerExtension {
	private final Property<AvroSourceFormat> format;

	private final Provider<AvroSourceFormat> standard;
	private final Provider<AvroSourceFormat> specificRecord;

	private final Provider<AvroScalaNumberType> scalaInt;

	@Getter(AccessLevel.NONE)
	private final ObjectFactory objects;

	@Inject
	public DefaultAvrohuggerExtension(ObjectFactory objects) {
		this.objects = objects;

		this.format = getProperty(AvroSourceFormat.class, objects.newInstance(Standard.class));

		this.standard = getProperty(AvroSourceFormat.class, objects.newInstance(Standard.class));
		this.specificRecord = getProperty(AvroSourceFormat.class, objects.newInstance(SpecificRecord.class));

		this.scalaInt = getProperty(AvroScalaNumberType.class, ScalaInt$.MODULE$);
	}

	private <T> Property<T> getProperty(Class<T> target, T value) {
		return objects.property(target).convention(value);
	}
}

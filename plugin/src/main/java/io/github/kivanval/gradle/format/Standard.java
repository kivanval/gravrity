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
package io.github.kivanval.gradle.format;

import avrohugger.format.Standard$;
import avrohugger.format.abstractions.SourceFormat;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public class Standard implements AvroSourceFormat {
	private final Provider<SourceFormat> sourceFormat;
	private final Property<AvroScalaTypes> types;

	@Getter(AccessLevel.NONE)
	private final ObjectFactory objects;

	@Inject
	public Standard(ObjectFactory objects) {
		this.objects = objects;
		this.sourceFormat = objects.property(SourceFormat.class).value(Standard$.MODULE$);
		this.types = defaultTypesProperty(objects);
	}

	@Override
	public AvroSourceFormat getCopy() {
		return new Standard(objects);
	}

	static Property<AvroScalaTypes> defaultTypesProperty(ObjectFactory objects) {
		return objects.property(AvroScalaTypes.class).value(objects.newInstance(DefaultAvroScalaTypes.class));
	}
}

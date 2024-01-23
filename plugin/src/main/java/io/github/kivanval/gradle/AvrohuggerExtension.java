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
package io.github.kivanval.gradle;

import static org.gradle.util.internal.ConfigureUtil.configure;

import avrohugger.types.AvroScalaNumberType;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import io.github.kivanval.gradle.format.AvroScalaTypes;
import io.github.kivanval.gradle.format.AvroSourceFormat;
import org.gradle.api.Action;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;

public interface AvrohuggerExtension {
	@Input
	Property<AvroSourceFormat> getFormat();

	Provider<AvroSourceFormat> getStandard();

	default Provider<AvroSourceFormat> standard(@DelegatesTo(AvroScalaTypes.class) Closure<?> configureClosure) {
		return sourceFormat(getStandard(), configureClosure);
	}

	default Provider<AvroSourceFormat> standard(Action<? super AvroScalaTypes> configureAction) {
		return sourceFormat(getStandard(), configureAction);
	}

	Provider<AvroSourceFormat> getSpecificRecord();

	default Provider<AvroSourceFormat> specificRecord(@DelegatesTo(AvroScalaTypes.class) Closure<?> configureClosure) {
		return sourceFormat(getSpecificRecord(), configureClosure);
	}

	default Provider<AvroSourceFormat> specificRecord(Action<? super AvroScalaTypes> configureAction) {
		return sourceFormat(getSpecificRecord(), configureAction);
	}

	Provider<AvroScalaNumberType> getScalaInt();

	private Provider<AvroSourceFormat> sourceFormat(Provider<AvroSourceFormat> avroSourceFormat,
			Closure<?> configureClosure) {
		return avroSourceFormat.map(AvroSourceFormat::getCopy).map(sourceFormat -> {
			sourceFormat.getTypes()
					.set(sourceFormat.getTypes().map(avroScalaTypes -> configure(configureClosure, avroScalaTypes)));
			return sourceFormat;
		});
	}

	private Provider<AvroSourceFormat> sourceFormat(Provider<AvroSourceFormat> avroSourceFormat,
			Action<? super AvroScalaTypes> configureAction) {
		return avroSourceFormat.map(AvroSourceFormat::getCopy).map(sourceFormat -> {
			sourceFormat.getTypes().set(sourceFormat.getTypes().map(avroScalaTypes -> {
				configureAction.execute(avroScalaTypes);
				return avroScalaTypes;
			}));
			return sourceFormat;
		});
	}
}

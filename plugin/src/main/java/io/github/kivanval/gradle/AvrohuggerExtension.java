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

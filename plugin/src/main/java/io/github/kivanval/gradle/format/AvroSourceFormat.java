package io.github.kivanval.gradle.format;

import avrohugger.format.abstractions.SourceFormat;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;

public interface AvroSourceFormat {
	Provider<SourceFormat> getSourceFormat();

	Property<AvroScalaTypes> getTypes();

	AvroSourceFormat getCopy();
}

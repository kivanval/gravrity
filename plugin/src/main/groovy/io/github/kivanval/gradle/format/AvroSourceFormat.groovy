package io.github.kivanval.gradle.format

import avrohugger.format.abstractions.SourceFormat
import org.gradle.api.provider.Property

interface AvroSourceFormat {
	Property<AvroScalaTypes> getTypes()

	SourceFormat getSourceFormat()

	AvroSourceFormat getCopy()
}

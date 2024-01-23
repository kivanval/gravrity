package io.github.kivanval.gradle

import avrohugger.types.AvroScalaNumberType
import io.github.kivanval.gradle.format.AvroSourceFormat
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

interface AvrohuggerExtension {
	Property<AvroSourceFormat> getFormat()

	Provider<AvroSourceFormat> getStandard()

	Provider<AvroSourceFormat> getSpecificRecord()

	Provider<AvroScalaNumberType> getScalaInt()
}

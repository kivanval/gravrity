package io.github.kivanval.gradle

import avrohugger.types.AvroScalaNumberType
import avrohugger.types.ScalaInt$
import groovy.transform.CompileStatic
import io.github.kivanval.gradle.format.AvroSourceFormat
import io.github.kivanval.gradle.format.SpecificRecord
import io.github.kivanval.gradle.format.Standard
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input

@CompileStatic
class DefaultAvrohuggerExtension implements AvrohuggerExtensionOperations {
	@Input
	Property<AvroSourceFormat> format

	final Provider<AvroSourceFormat> standard
	final Provider<AvroSourceFormat> specificRecord

	final Provider<AvroScalaNumberType> scalaInt

	private final ObjectFactory objects

	@Inject
	DefaultAvrohuggerExtension(ObjectFactory objects) {
		this.objects = objects

		this.format = objects.property(AvroSourceFormat).convention(objects.<Standard>newInstance(Standard))

		this.standard = getProperty(AvroSourceFormat, objects.<Standard>newInstance(Standard))
		this.specificRecord = getProperty(AvroSourceFormat, objects.<SpecificRecord>newInstance(SpecificRecord))

		this.scalaInt = getProperty(AvroScalaNumberType, ScalaInt$.MODULE$)
	}

	private <T> Property<T> getProperty(Class<T> target, T value) {
		objects.property(target).convention(value)
	}
}

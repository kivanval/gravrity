package io.github.kivanval.gradle.format

import avrohugger.format.SpecificRecord$
import avrohugger.format.abstractions.SourceFormat
import groovy.transform.CompileStatic
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

@CompileStatic
class SpecificRecord implements AvroSourceFormat {
	final SourceFormat sourceFormat

	@Input
	Property<AvroScalaTypes> types

	private final ObjectFactory objects

	@Inject
	SpecificRecord(ObjectFactory objects) {
		this.objects = objects
		this.sourceFormat = SpecificRecord$.MODULE$
		this.types = objects.property(AvroScalaTypes)
				.value(objects.<DefaultAvroScalaTypes>newInstance(DefaultAvroScalaTypes)
				.initBy(sourceFormat.defaultTypes()))
	}

	@Override
	SpecificRecord getCopy() {
		new SpecificRecord(objects)
	}
}

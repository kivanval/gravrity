package io.github.kivanval.gradle.format

import avrohugger.format.Standard$
import avrohugger.format.abstractions.SourceFormat
import groovy.transform.CompileStatic
import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

@CompileStatic
class Standard implements AvroSourceFormat {
	final SourceFormat sourceFormat

	@Input
	Property<AvroScalaTypes> types

	private final ObjectFactory objects

	@Inject
	Standard(ObjectFactory objects) {
		this.objects = objects
		this.sourceFormat = Standard$.MODULE$
		this.types = objects.property(AvroScalaTypes)
				.value(objects.<DefaultAvroScalaTypes>newInstance(DefaultAvroScalaTypes)
				.initBy(sourceFormat.defaultTypes()))
	}

	@Override
	Standard getCopy() {
		new Standard(objects)
	}
}

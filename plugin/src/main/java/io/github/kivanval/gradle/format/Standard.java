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

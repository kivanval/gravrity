package io.github.kivanval.gradle.format;

import avrohugger.format.SpecificRecord$;
import avrohugger.format.abstractions.SourceFormat;
import avrohugger.types.JavaEnum$;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.Input;

@Getter(onMethod_ = {@Input})
public class SpecificRecord implements AvroSourceFormat {
	private final Provider<SourceFormat> sourceFormat;
	private final Property<AvroScalaTypes> types;

	@Getter(AccessLevel.NONE)
	private final ObjectFactory objects;

	@Inject
	public SpecificRecord(ObjectFactory objects) {
		this.objects = objects;
		this.sourceFormat = objects.property(SourceFormat.class).convention(SpecificRecord$.MODULE$);
		this.types = defaultTypesProperty(objects);
	}

	static Property<AvroScalaTypes> defaultTypesProperty(ObjectFactory objects) {
		return objects.property(AvroScalaTypes.class).value(objects.property(AvroScalaTypes.class)
				.value(objects.newInstance(DefaultAvroScalaTypes.class)).map(avroScalaTypes -> {
					avroScalaTypes.getEnumType().set(JavaEnum$.MODULE$);
					return avroScalaTypes;
				}));
	}

	@Override
	public AvroSourceFormat getCopy() {
		return new SpecificRecord(objects);
	}
}

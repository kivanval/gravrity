package io.github.kivanval.gradle

import static org.gradle.util.internal.ConfigureUtil.configure

import groovy.transform.CompileStatic
import io.github.kivanval.gradle.format.AvroScalaTypes
import io.github.kivanval.gradle.format.AvroSourceFormat
import org.gradle.api.Action
import org.gradle.api.provider.Provider

@CompileStatic
trait AvrohuggerExtensionOperations implements AvrohuggerExtension {
	Provider<AvroSourceFormat> standard(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
		sourceFormat(standard, configureClosure)
	}

	Provider<AvroSourceFormat> standard(Action<? super AvroScalaTypes> configureAction) {
		sourceFormat(standard, configureAction)
	}

	Provider<AvroSourceFormat> specificRecord(@DelegatesTo(AvroScalaTypes) Closure<?> configureClosure) {
		sourceFormat(specificRecord, configureClosure)
	}

	Provider<AvroSourceFormat> specificRecord(Action<? super AvroScalaTypes> configureAction) {
		sourceFormat(specificRecord, configureAction)
	}

	private Provider<AvroSourceFormat> sourceFormat(Provider<AvroSourceFormat> avroSourceFormat,
			Closure<?> configureClosure) {
		avroSourceFormat
				.map(AvroSourceFormat.&getCopy)
				.map { sourceSet ->
					sourceSet.types
							.set(sourceSet.types
							.map { avroScalaTypes ->
								configure(configureClosure, avroScalaTypes)
							})
					sourceSet
				}
	}

	private Provider<AvroSourceFormat> sourceFormat(Provider<AvroSourceFormat> avroSourceFormat,
			Action<? super AvroScalaTypes> configureAction) {
		avroSourceFormat
				.map(AvroSourceFormat.&getCopy)
				.map { sourceFormat ->
					sourceFormat.types.set(sourceFormat.types
							.map { avroScalaTypes ->
								configureAction.execute(avroScalaTypes)
								avroScalaTypes
							})
					sourceFormat
				}
	}
}

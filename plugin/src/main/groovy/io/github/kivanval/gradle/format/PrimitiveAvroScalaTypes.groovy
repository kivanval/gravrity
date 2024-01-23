package io.github.kivanval.gradle.format

import avrohugger.types.AvroScalaNumberType
import avrohugger.types.ScalaInt$

trait PrimitiveAvroScalaTypes {
    AvroScalaNumberType scalaInt() {
        ScalaInt$.MODULE$
    }
}
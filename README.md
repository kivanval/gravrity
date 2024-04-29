# 🇺🇦 HELP UKRAINE

I'm the creator of this project.
My country, Ukraine, [is being invaded by the Russian Federation, right now](https://war.ukraine.ua). If you want to help my country to fight, consider donating to [charity supporting Ukrainian army](https://www.comebackalive.in.ua/). More options is described on [support ukraine](https://supportukrainenow.org/) site.

# Avrohugger Plugin for Gradle

A Gradle plugin that compiles [Apache Avro](https://avro.apache.org/docs/1.11.1/) files (```*.avpr, *.avsc, *.avdl```) 
into the corresponding Scala files in your project using the [Avrohugger](https://github.com/julianpeeters/avrohugger).
It has two main functions:
1. Collects all the necessary information for the Avohugger Generator, which generates Scala sources from Apache Avro files.
2. Adds the generated Scala source files to the input of the Scala sourceSet, so that they can be compiled along with your Scala sources.

## Latest Version

The latest version is 1.0.0. It requires at least Gradle 7.2 and Java 8.
- Groovy
```groovy
plugins {
  id 'io.github.kivanval.avrohugger' version '1.0.0'
}
```
- Kotlin
```kotlin
plugins {
  id("io.github.kivanval.avrohugger") version "1.0.0"
}
```

## Development Version

To try out the head version, you can download the source and build it with ```./gradlew publishToMavenLocal```, then in ```settings.gradle```:
```groovy
pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenLocal()
  }
}
```
And in ```build.gradle```:
- Groovy
```groovy
plugins {
  id 'com.google.protobuf' version '1.0.0-SNAPSHOT'
}
```
- Kotlin
```kotlin
plugins {
  id("com.google.protobuf") version "1.0.0-SNAPSHOT"
}
```

## Configuring Avrohugger generation

The Avrohugger plugin assumes that the Apache Avro files (```*.avpr, *.avsc, *.avdl```) 
are organized in the same way as Scala source files, in sourceSets. 
The files generated by the Avrohugger Generator are added to the Scala sources 
before executing ```./gradlew compileScala```.

### Customizing source directories

The plugin adds a new source directory named ```avro``` alongside ```scala``` to every sourceSet. 
By default, it includes all ```*.avpr, *.avsc, *.avdl``` files under ```src/$sourceSetName/avro```. 
You can customize it in the same way as you would customize the scala sources.
```groovy
sourceSets {
    main {
        avro {
            // In addition to the default 'src/main/avro'
            srcDir 'src/main/avro-schemas'
            include '**/*.json'
            // Change default output path 'generated/sources/avrohugger/scala/main'
            destinationDirectory = file("$rootDir/someDir")
        }
  }
    test {
        avro {
            // In addition to the default 'src/test/avro'
            srcDir 'src/test/avro-schemas'
        }
    }
}
```

### Customizing Avrohugger generation

The plugin adds a ```avrohugger``` extension to the project.
It provides all the configurations necessary for the generator.

```groovy
avrohugger {
    // There are two types of generation: standard and specific. 
    // Choose the one you need and change the standard configuration for the types if necessary.
    format = specific {
        intType = scalaInt
        decimalType = scalaBigDecimal(roundingMode.CEILING())
    }
    // Mapping avro namespaces to scala packages
    namespaceMapping = ['com.example': 'io.github.kivanval.avrohugger']
    // Flag related to case classes limitation in Scala versions <= 2.10
    restrictedFieldNumber = false
}
```
#### Default configuration

To understand the standard type mappings for the plugin, 
I recommend checking out the [Avrohugger](https://github.com/julianpeeters/avrohugger).

```groovy
avrohugger {
    // default type mapping from avrohugger
    format = standard
    namespaceMapping = []
    restrictedFieldNumber = false
}
```

#### Specific format

Using the specific format make sure you have 
a dependency on [Apache Avro](https://mvnrepository.com/artifact/org.apache.avro/avro). 
At the moment this has to be done manually.

## Sandbox

If you want to manually test the plugin, 
use [sandbox](https://github.com/kivanval/gradle-avrohugger/tree/develop/sandbox) for this purpose.
Run ```./gradlew build``` under the sandbox directory to test it out.

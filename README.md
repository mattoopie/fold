# Advanced folds for Kotlin

[![Build and test](https://github.com/mattoopie/fold/actions/workflows/build-test.yaml/badge.svg)](https://github.com/mattoopie/fold/actions/workflows/build-test.yaml)
[![Maven central](https://img.shields.io/maven-central/v/org.eend/fold?label=Maven%20Central&logo=apachemaven)](https://mvnrepository.com/artifact/org.eend/fold)

## Description

More advanced fold methods for Kotlin, for use only after mastering:

1. do while
2. fold
3. foldRight
4. foldRightIndexed

## Installation

The library is available as Maven artifact on [Maven Central](https://mvnrepository.com/artifact/org.eend/fold).
You can use it as a dependency in your project, for example using Gradle (Kotlin DSL):

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eend:fold:2.0.2")
}
```

## Available fold methods

### Sandwich

* foldSandwich
* foldSandwichIndexed

Accumulates the lambda result based on the initial value, while simultaneously
providing the first and last item of the list, working inwards towards the center of the list.

If there is an odd number of items, the center item is not provided and is thus lost.

**Usage**

```kotlin
val list = listOf("S", "n", "w", "c", "h", "i", "d", "a")
val foldedSandwich = list.foldSandwich("") { first: String, currentValue: String, last: String ->
    "$currentValue$first$last"
}
println(foldedSandwich) // Prints: "Sandwich"
```

### Zigzag

* foldZigzag
* foldZigzagIndexed

Accumulates the lambda result based on the initial value, while alternating
between the first and last item of the list. Starts with the first item.

**Usage**

```kotlin
val list = listOf("Z", "g", "a", "g", "z", "i")
val result = list.foldZigzag("") { currentValue: String, next: String ->
    "$currentValue$next"
}
println(result) // Prints: "Zigzag"
```

### Random

* foldRandom
* foldRandomIndexed

Accumulates the lambda result based on the initial value, while randomly
picking the next item. It is guaranteed that all items are provided to the lambda.

**Usage**

```kotlin
val list = listOf("R", "a", "n", "d", "o", "m")
val result = list.foldRandom("") { currentValue: String, next: String ->
    "$currentValue$next"
}
println(result) // Prints one of: "admonR", "naRdmo", "danomR", and so on.
```

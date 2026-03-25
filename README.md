# Advanced folds for Kotlin

[![Build and test](https://github.com/mattoopie/fold/actions/workflows/build-test.yaml/badge.svg)](https://github.com/mattoopie/fold/actions/workflows/build-test.yaml)
[![Maven central](https://img.shields.io/maven-central/v/org.eend/fold?label=Maven%20Central&logo=apachemaven)](https://mvnrepository.com/artifact/org.eend/fold)

## Description

More advanced fold methods for Kotlin, for use only after mastering:

1. `do {} while()`
2. `fold`
3. `foldRight`
4. `foldRightIndexed`

## Installation

The library is available as Maven artifact on [Maven Central](https://mvnrepository.com/artifact/org.eend/fold).
The latest version number can be found there
or from [the latest GitHub release](https://github.com/mattoopie/fold/releases/latest).
You can use it as a dependency in your project, for example using Gradle (Kotlin DSL):

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.eend:fold:x.y.z")
}
```

## Available fold methods

### Sandwich

* `foldSandwich`
* `foldSandwichIndexed`
* `foldSandwichNullable`

Accumulates the lambda result based on the initial value, while simultaneously
providing the first and last item of the list, working inwards towards the center of the list.

If there is an odd number of items, the center item is not provided and is thus lost.
An alternative for this is using `foldSandwichNullable`, which keeps the center item but also
adds a null value.

**Usage**

```kotlin
val list = listOf("S", "n", "w", "c", "h", "i", "d", "a")
val foldedSandwich = list.foldSandwich("") { first: String, currentValue: String, last: String ->
    "$currentValue$first$last"
}
println(foldedSandwich) // Prints: "Sandwich"
```

### Center Out

* `foldCenterOut`
* `foldCenterOutIndexed`

Accumulates the lambda result by starting in the middle of the list and moving out towards both ends.
For even-sized lists it starts with the left-middle item.

**Usage**

```kotlin
val list = listOf("O", "u", "t", "n", "e", "r")
val result = list.foldCenterOut("") { currentValue: String, next: String ->
    "$currentValue$next"
}
println(result) // Prints: "tnOeur"
```

### Zigzag

* `foldZigzag`
* `foldZigzagIndexed`

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

### Riffle

* `foldRiffle`
* `foldRiffleIndexed`

Accumulates the lambda result by interleaving the first half of the list with the second half.
If the list has an odd size, the first half contributes the extra item.

**Usage**

```kotlin
val list = listOf("R", "f", "l", "i", "f", "e")
val result = list.foldRiffle("") { currentValue: String, next: String ->
    "$currentValue$next"
}
println(result) // Prints: "Riflef"
```

### Mitosis

* `foldMitosis`
* `foldMitosisIndexed`

Accumulates the lambda result by visiting the first item of the current queue segment,
then splitting the remainder into two smaller queued segments.

**Usage**

```kotlin
val list = listOf("M", "i", "t", "o", "s", "i", "s")
val result = list.foldMitosis("") { currentValue: String, next: String ->
    "$currentValue$next"
}
println(result) // Prints: "Mistois"
```

### Palindrone

* `foldPalindrone`
* `foldPalindroneIndexed`

Accumulates the lambda result by visiting the first half of the list,
then revisiting that same half in reverse order.
For odd-sized lists, the center item is part of the visited half.

**Usage**

```kotlin
val list = listOf(1, 2, 3, 4, 5)
val result = list.foldPalindrone("") { currentValue: String, next: Int ->
    "$currentValue$next"
}
println(result) // Prints: "123321"
```

### Random

* `foldRandom`
* `foldRandomIndexed`

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

### Russian Roulette

* `foldRussianRoulette`
* `foldRussianRouletteIndexed`

Accumulates the lambda result in order, but every item has a chance to disappear before it is visited.

**Usage**

```kotlin
val list = listOf(1, 2, 3, 4, 5, 6)
val result = list.foldRussianRoulette("", chanceOfDisappearing = 0.5f) { currentValue: String, next: Int ->
    "$currentValue$next"
}
println(result) // Prints a subsequence, for example: "246"
```

### Tournament

* `foldTournament`
* `foldTournamentIndexed`

Pairs neighbours into rounds and lets the lambda decide which item advances.
If a round has an odd number of items, the final item advances automatically.

**Usage**

```kotlin
val list = listOf("ant", "wolf", "emu", "bear", "ox")
val winner = list.foldTournament { left: String, right: String ->
    if (left.length >= right.length) left else right
}
println(winner) // Prints: "wolf"
```

### Chaos

* `foldChaos`

Better do not use this one. It is guaranteed to cause a mess.

**Usage**

```kotlin
val list = listOf("C", "h", "a", "o", "s", 5)
val result = list.foldChaos("") { acc, next ->
    acc + next
}
println(result) // Different result every time, for example: sBgVogd-1207019598hs5o
```

### Quicksand

* `foldQuicksand`
* `foldQuicksandIndexed`

Accumulates the lambda result in order, but every slow step sinks the accumulator deeper into quicksand.
The deeper it sinks, the more garbled the output becomes.

**Usage**

```kotlin
val list = listOf("Q", "u", "i", "c", "k", "s", "a", "n", "d")
val result = list.foldQuicksand("") { currentValue: String, next: String ->
    Thread.sleep(15)
    "$currentValue$next"
}
println(result) // Prints something increasingly garbled, for example: "#@icksand"
```

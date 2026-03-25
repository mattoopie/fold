package org.eend.fold

import kotlin.random.Random

/**
 * Accumulates the result in order, unless an item disappears before it can be visited.
 */
inline fun <T, ACC> List<T>.foldRussianRoulette(
    initial: ACC,
    chanceOfDisappearing: Float = 1f / 6f,
    randomInstance: Random = Random.Default,
    transform: (ACC, nextValue: T) -> ACC
): ACC =
    foldRussianRouletteIndexed(initial, chanceOfDisappearing, randomInstance) { _, current, nextValue ->
        transform(current, nextValue)
    }

/**
 * Accumulates the result in order, unless an item disappears before it can be visited.
 * Also provides the index of each surviving item.
 */
inline fun <T, ACC> List<T>.foldRussianRouletteIndexed(
    initial: ACC,
    chanceOfDisappearing: Float = 1f / 6f,
    randomInstance: Random = Random.Default,
    transform: (index: Int, ACC, nextValue: T) -> ACC
): ACC {
    require(chanceOfDisappearing in 0f..1f) { "chanceOfDisappearing must be between 0 and 1" }

    var current = initial
    forEachIndexed { index, nextValue ->
        if (randomInstance.nextFloat() >= chanceOfDisappearing) {
            current = transform(index, current, nextValue)
        }
    }
    return current
}

package org.eend.fold

import kotlin.random.Random

/**
 * Accumulates the result by iterating over each item of the list in a random order.
 * It is guaranteed that all items are iterated over.
 */
inline fun <T, ACC> List<T>.foldRandom(
    initial: ACC,
    randomInstance: Random = Random.Default,
    transform: (ACC, nextValue: T) -> ACC
): ACC =
    this.shuffled(randomInstance).fold(initial) { currentAcc, nextItem ->
        transform(currentAcc, nextItem)
    }

/**
 * Accumulates the result by iterating over each item of the list in a random order.
 * It is guaranteed that all items are iterated over. Also provides the index of the current item.
 */
inline fun <T, ACC> List<T>.foldRandomIndexed(
    initial: ACC,
    randomInstance: Random = Random.Default,
    transform: (Int, ACC, nextValue: T) -> ACC
): ACC =
    this.shuffled(randomInstance).foldIndexed(initial) { index, currentAcc, nextItem ->
        transform(index, currentAcc, nextItem)
    }

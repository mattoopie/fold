package org.eend.fold

import kotlin.math.roundToInt
import kotlin.random.Random

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

@PublishedApi
internal const val CHANCE_OF_CHAOS = 0.4

/**
 * Better do not use this one. It is guaranteed to cause a mess.
 */
inline fun <T, ACC> List<T>.foldChaos(
    initial: ACC,
    randomInstance: Random = Random.Default,
    transform: (ACC, nextValue: T) -> ACC
): ACC {
    var currentAcc = initial
    val randomNumberOfElements = (this.size * (randomInstance.nextFloat() * 2)).roundToInt()
    for (i in 0..randomNumberOfElements) {
        val randomIndex = (randomInstance.nextFloat() * (this.size - 1)).roundToInt()
        val nextValue = this[randomIndex]
        currentAcc = if (randomInstance.nextFloat() < CHANCE_OF_CHAOS) {
            @Suppress("UNCHECKED_CAST")
            transform(currentAcc, introduceChaos(nextValue, randomInstance) as T)
        } else {
            transform(currentAcc, nextValue)
        }
    }
    return currentAcc
}

@PublishedApi
internal fun introduceChaos(
    value: Any?,
    randomInstance: Random = Random.Default
): Any? = when (value) {
    is Int -> randomInstance.nextInt()
    is Long -> randomInstance.nextLong()
    is Boolean -> randomInstance.nextBoolean()
    is Double -> randomInstance.nextDouble()
    is Float -> randomInstance.nextInt() * randomInstance.nextFloat() // If this overflows it is random as well
    is String -> (0..(value.length * (randomInstance.nextFloat() * 2)).roundToInt()).map {
        charPool.random(randomInstance)
    }.joinToString("")

    is List<*> -> value.foldChaos(listOf<Any?>(), randomInstance) { chaoticItems, chaoticItem ->
        chaoticItems + listOf(
            chaoticItem
        )
    }

    else -> value
}

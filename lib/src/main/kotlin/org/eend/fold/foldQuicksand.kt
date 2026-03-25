package org.eend.fold

import kotlin.math.ceil
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TimeSource

private val sandCharPool: List<Char> = listOf('~', '#', '%', '&', '?', '@', '*', '!')

@PublishedApi
internal val QUICKSAND_STEP_DURATION: Duration = 15.milliseconds

@PublishedApi
internal const val SINK_DEPTH_UNTIL_SUBMERGED = 20

@PublishedApi
internal const val MAX_OTHER_TYPE_CHAOS_PASSES = 3

/**
 * Accumulates the result by iterating over each item of the list in order.
 * Slow transform steps sink the accumulator deeper into quicksand,
 * which gradually garbles the output.
 */
inline fun <T, ACC> List<T>.foldQuicksand(
    initial: ACC,
    randomInstance: Random = Random.Default,
    timeSource: TimeSource = TimeSource.Monotonic,
    transform: (ACC, nextValue: T) -> ACC
): ACC =
    foldQuicksandIndexed(initial, randomInstance, timeSource) { _, current, nextValue ->
        transform(current, nextValue)
    }

/**
 * Accumulates the result by iterating over each item of the list in order.
 * Slow transform steps sink the accumulator deeper into quicksand,
 * which gradually garbles the output.
 * Also provides the index of the current item.
 */
inline fun <T, ACC> List<T>.foldQuicksandIndexed(
    initial: ACC,
    randomInstance: Random = Random.Default,
    timeSource: TimeSource = TimeSource.Monotonic,
    transform: (index: Int, ACC, nextValue: T) -> ACC
): ACC {
    var currentAcc = initial
    var sinkDepth = 0
    forEachIndexed { index, nextValue ->
        val timeMark = timeSource.markNow()
        currentAcc = transform(index, currentAcc, nextValue)
        sinkDepth += sinkDepthIncreaseFor(timeMark.elapsedNow())
        @Suppress("UNCHECKED_CAST")
        currentAcc = introduceQuicksand(currentAcc, sinkDepth, randomInstance) as ACC
    }
    return currentAcc
}

@PublishedApi
internal fun sinkDepthIncreaseFor(elapsed: Duration): Int {
    if (elapsed <= Duration.ZERO) {
        return 0
    }
    return ceil(elapsed.inWholeNanoseconds.toDouble() / QUICKSAND_STEP_DURATION.inWholeNanoseconds.toDouble()).toInt()
}

@PublishedApi
internal fun introduceQuicksand(
    value: Any?,
    sinkDepth: Int,
    randomInstance: Random = Random.Default
): Any? {
    if (sinkDepth <= 0 || value == null) {
        return value
    }
    return when (value) {
        is String -> garbleString(value, sinkDepth, randomInstance)
        is List<*> -> value.map { introduceQuicksand(it, sinkDepth, randomInstance) }
        else -> garbleOtherValue(value, sinkDepth, randomInstance)
    }
}

private fun garbleString(
    value: String,
    sinkDepth: Int,
    randomInstance: Random
): String {
    val candidateIndexes = value.indices.filter { !value[it].isWhitespace() }
    if (candidateIndexes.isEmpty()) {
        return value
    }
    val garbleRatio = (sinkDepth.toDouble() / SINK_DEPTH_UNTIL_SUBMERGED.toDouble()).coerceAtMost(1.0)
    val garbleCount = ceil(candidateIndexes.size * garbleRatio).toInt().coerceAtMost(candidateIndexes.size)
    if (garbleCount <= 0) {
        return value
    }

    val chars = value.toCharArray()
    candidateIndexes.take(garbleCount).forEach { index ->
        chars[index] = nextSandChar(chars[index], randomInstance)
    }
    return chars.concatToString()
}

private fun nextSandChar(currentChar: Char, randomInstance: Random): Char {
    var replacement = sandCharPool.random(randomInstance)
    while (replacement == currentChar && sandCharPool.size > 1) {
        replacement = sandCharPool.random(randomInstance)
    }
    return replacement
}

private fun garbleOtherValue(
    value: Any,
    sinkDepth: Int,
    randomInstance: Random
): Any? {
    val garbleRatio = (sinkDepth.toDouble() / SINK_DEPTH_UNTIL_SUBMERGED.toDouble()).coerceAtMost(1.0)
    val passes = ceil(MAX_OTHER_TYPE_CHAOS_PASSES * garbleRatio).toInt().coerceAtLeast(1)
    var garbledValue: Any? = value
    repeat(passes) {
        garbledValue = introduceChaos(garbledValue, randomInstance)
    }
    return garbledValue
}

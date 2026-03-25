package org.eend.fold

/**
 * Accumulates the result by visiting the first half of the list,
 * then revisiting that same half in reverse order.
 * For odd-sized lists, the center item belongs to the first half.
 */
inline fun <T, ACC> List<T>.foldPalindrone(
    initial: ACC,
    transform: (ACC, nextValue: T) -> ACC
): ACC =
    foldPalindroneIndexed(initial) { _, current, nextValue ->
        transform(current, nextValue)
    }

/**
 * Accumulates the result by visiting the first half of the list,
 * then revisiting that same half in reverse order.
 * For odd-sized lists, the center item belongs to the first half.
 * Also provides the index of the current item.
 */
inline fun <T, ACC> List<T>.foldPalindroneIndexed(
    initial: ACC,
    transform: (index: Int, ACC, nextValue: T) -> ACC
): ACC {
    var current = initial
    val firstHalfEndExclusive = (size + 1) / 2
    for (index in 0 until firstHalfEndExclusive) {
        current = transform(index, current, this[index])
    }
    for (index in (firstHalfEndExclusive - 1) downTo 0) {
        current = transform(index, current, this[index])
    }
    return current
}

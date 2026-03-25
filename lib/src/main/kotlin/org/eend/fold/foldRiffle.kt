package org.eend.fold

/**
 * Accumulates the result by interleaving the first and second half of the list.
 * If the list has an odd size, the first half contributes the extra item.
 */
inline fun <T, ACC> List<T>.foldRiffle(
    initial: ACC,
    transform: (ACC, nextValue: T) -> ACC
): ACC =
    foldRiffleIndexed(initial) { _, current, nextValue ->
        transform(current, nextValue)
    }

/**
 * Accumulates the result by interleaving the first and second half of the list.
 * If the list has an odd size, the first half contributes the extra item.
 * Also provides the index of the current item.
 */
inline fun <T, ACC> List<T>.foldRiffleIndexed(
    initial: ACC,
    transform: (index: Int, ACC, nextValue: T) -> ACC
): ACC {
    var current = initial
    val splitIndex = (size + 1) / 2
    var leftIndex = 0
    var rightIndex = splitIndex
    while (leftIndex < splitIndex || rightIndex < size) {
        if (leftIndex < splitIndex) {
            current = transform(leftIndex, current, this[leftIndex])
            leftIndex += 1
        }
        if (rightIndex < size) {
            current = transform(rightIndex, current, this[rightIndex])
            rightIndex += 1
        }
    }
    return current
}

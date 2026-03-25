package org.eend.fold

/**
 * Accumulates the result by taking the next queued segment, visiting its first item,
 * and splitting the remaining segment into two smaller queued segments.
 */
inline fun <T, ACC> List<T>.foldMitosis(
    initial: ACC,
    transform: (ACC, nextValue: T) -> ACC
): ACC =
    foldMitosisIndexed(initial) { _, current, nextValue ->
        transform(current, nextValue)
    }

/**
 * Accumulates the result by taking the next queued segment, visiting its first item,
 * and splitting the remaining segment into two smaller queued segments.
 * Also provides the index of the current item.
 */
inline fun <T, ACC> List<T>.foldMitosisIndexed(
    initial: ACC,
    transform: (index: Int, ACC, nextValue: T) -> ACC
): ACC {
    var current = initial
    if (isEmpty()) {
        return current
    }

    val queue = ArrayDeque<IntRange>()
    queue.addLast(indices)
    while (queue.isNotEmpty()) {
        val currentRange = queue.removeFirst()
        current = transform(currentRange.first, current, this[currentRange.first])

        val remainderStart = currentRange.first + 1
        if (remainderStart > currentRange.last) {
            continue
        }

        val remainderSize = currentRange.last - remainderStart + 1
        val leftSize = (remainderSize + 1) / 2
        val leftEnd = remainderStart + leftSize - 1

        queue.addLast(remainderStart..leftEnd)
        if (leftEnd < currentRange.last) {
            queue.addLast((leftEnd + 1)..currentRange.last)
        }
    }
    return current
}

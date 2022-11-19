package org.eend.fold

/**
 * Accumulates the result by zigzagging between the first and last remaining item in the list.
 * Starts at the first item.
 */
inline fun <T, ACC> List<T>.foldZigzag(
    initial: ACC,
    transform: (ACC, nextValue: T) -> ACC
): ACC {
    var current: ACC = initial
    if (!isEmpty()) {
        var count = 0
        val startIterator = listIterator()
        val endIterator = listIterator(size)
        while (count < this.size) {
            current = transform(current, startIterator.next())
            if (this.size - count > 1) {
                current = transform(current, endIterator.previous())
            }
            count += 2
        }
    }
    return current
}

/**
 * Accumulates the result by going from the start and back of the list simultaneously.
 * If an odd number of items is in the initial list, the center value is lost.
 * Also provides the index of the current item.
 */
inline fun <T, ACC> List<T>.foldZigzagIndexed(
    initial: ACC,
    transform: (index: Int, ACC, nextValue: T) -> ACC
): ACC {
    var current: ACC = initial
    if (!isEmpty()) {
        var count = 0
        val startIterator = listIterator()
        val endIterator = listIterator(size)
        while (count < this.size) {
            current = transform(startIterator.nextIndex(), current, startIterator.next())
            if (this.size - count > 1) {
                current = transform(endIterator.previousIndex(), current, endIterator.previous())
            }
            count += 2
        }
    }
    return current
}

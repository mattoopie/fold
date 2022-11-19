package org.eend.fold

/**
 * Accumulates the result by zigzagging between the first and last remaining item in the list.
 * Starts at the first item.
 */
inline fun <T, ACC> List<T>.foldZigzag(
    initial: ACC,
    transform: (nextValue: T, ACC) -> ACC
): ACC {
    var current: ACC = initial
    if (!isEmpty()) {
        var count = 0
        val startIterator = listIterator()
        val endIterator = listIterator(size)
        while (count < this.size) {
            current = transform(startIterator.next(), current)
            if (this.size - count > 1) {
                current = transform(endIterator.previous(), current)
            }
            count += 2
        }
    }
    return current
}

/**
 * Accumulates the resulting sandwich by going from the start and back of the list simultaneously.
 * If an odd number of items is in the initial list, the center value is lost.
 * Also provides the indexes of the current slices of bread.
 */
inline fun <T, ACC> List<T>.foldZigzagIndexed(
    initial: ACC,
    transform: (index: Int, nextValue: T, ACC) -> ACC
): ACC {
    var current: ACC = initial
    if (!isEmpty()) {
        var count = 0
        val startIterator = listIterator()
        val endIterator = listIterator(size)
        while (count < this.size) {
            current = transform(startIterator.nextIndex(), startIterator.next(), current)
            if (this.size - count > 1) {
                current = transform(endIterator.previousIndex(), endIterator.previous(), current)
            }
            count += 2
        }
    }
    return current
}

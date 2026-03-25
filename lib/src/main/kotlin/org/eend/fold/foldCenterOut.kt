package org.eend.fold

/**
 * Accumulates the result by starting in the center of the list and working outwards.
 * For even-sized lists it starts with the left-center item.
 */
inline fun <T, ACC> List<T>.foldCenterOut(
    initial: ACC,
    transform: (ACC, nextValue: T) -> ACC
): ACC =
    foldCenterOutIndexed(initial) { _, current, nextValue ->
        transform(current, nextValue)
    }

/**
 * Accumulates the result by starting in the center of the list and working outwards.
 * For even-sized lists it starts with the left-center item.
 * Also provides the index of the current item.
 */
inline fun <T, ACC> List<T>.foldCenterOutIndexed(
    initial: ACC,
    transform: (index: Int, ACC, nextValue: T) -> ACC
): ACC {
    var current = initial
    if (isEmpty()) {
        return current
    }

    var leftIndex = (size - 1) / 2
    var rightIndex = size / 2
    while (leftIndex >= 0 || rightIndex < size) {
        if (leftIndex == rightIndex) {
            current = transform(leftIndex, current, this[leftIndex])
        } else {
            if (leftIndex >= 0) {
                current = transform(leftIndex, current, this[leftIndex])
            }
            if (rightIndex < size) {
                current = transform(rightIndex, current, this[rightIndex])
            }
        }
        leftIndex -= 1
        rightIndex += 1
    }
    return current
}

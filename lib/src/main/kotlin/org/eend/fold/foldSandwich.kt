package org.eend.fold

/**
 * Accumulates the resulting sandwich by going from the start and back of the list simultaneously.
 * If an odd number of items is in the initial list, the center value is lost.
 */
inline fun <BREAD, SANDWICH> List<BREAD>.foldSandwich(
    initialSandwich: SANDWICH,
    toppingAdder: (bottomSlice: BREAD, SANDWICH, topSlice: BREAD) -> SANDWICH
): SANDWICH {
    var currentSandwich: SANDWICH = initialSandwich
    if (isNotEmpty()) {
        val centerIndex = this.centerIndex()
        val startIterator = listIterator()
        val endIterator = listIterator(size)
        while (startIterator.nextIndex() < centerIndex) {
            currentSandwich = toppingAdder(startIterator.next(), currentSandwich, endIterator.previous())
        }
    }
    return currentSandwich
}

/**
 * Accumulates the resulting sandwich by going from the start and back of the list simultaneously.
 * If an odd number of items is in the initial list, the item is added as top or bottom slice
 * according to the [useCenterValueAsTopSlice] parameter.
 */
fun <BREAD, SANDWICH> List<BREAD>.foldSandwichNullable(
    initialSandwich: SANDWICH,
    useCenterValueAsTopSlice: Boolean = true,
    toppingAdder: (bottomSlice: BREAD?, SANDWICH, topSlice: BREAD?) -> SANDWICH
): SANDWICH {
    var currentSandwich: SANDWICH = initialSandwich
    if (isNotEmpty()) {
        val centerIndex = this.centerIndex()
        val startIterator = listIterator()
        val endIterator = listIterator(size)
        while (startIterator.nextIndex() < centerIndex) {
            val previous = endIterator.previous()
            val next = startIterator.next()
            currentSandwich = toppingAdder(next, currentSandwich, previous)
        }
        if (this.hasOddSize()) {
            val centerValue = startIterator.next()
            currentSandwich = if (useCenterValueAsTopSlice) {
                toppingAdder(null, currentSandwich, centerValue)
            } else {
                toppingAdder(centerValue, currentSandwich, null)
            }
        }
    }
    return currentSandwich
}

/**
 * Accumulates the resulting sandwich by going from the start and back of the list simultaneously.
 * If an odd number of items is in the initial list, the center value is lost.
 * Also provides the indexes of the current slices of bread.
 */
inline fun <BREAD, SANDWICH> List<BREAD>.foldSandwichIndexed(
    initialSandwich: SANDWICH,
    toppingAdder: (bottomIndex: Int, bottomSlice: BREAD, SANDWICH, topSlice: BREAD, topIndex: Int) -> SANDWICH
): SANDWICH {
    var currentSandwich: SANDWICH = initialSandwich
    if (isNotEmpty()) {
        val centerIndex = this.centerIndex()
        val bottomSliceIterator = listIterator()
        val topSliceIterator = listIterator(size)
        while (bottomSliceIterator.nextIndex() < centerIndex) {
            val nextBottomSliceIndex = bottomSliceIterator.nextIndex()
            val nextTopSliceIndex = topSliceIterator.previousIndex()
            currentSandwich = toppingAdder(
                nextBottomSliceIndex,
                bottomSliceIterator.next(),
                currentSandwich,
                topSliceIterator.previous(),
                nextTopSliceIndex
            )
        }
    }
    return currentSandwich
}

@PublishedApi
internal fun Collection<*>.centerIndex() = this.size / 2

@PublishedApi
internal fun Collection<*>.hasOddSize() = this.size % 2 != 0

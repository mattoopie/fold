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
    if (!isEmpty()) {
        val halfIndex = this.size / 2
        val startIterator = listIterator()
        val endIterator = listIterator(size)
        while (startIterator.nextIndex() < halfIndex) {
            currentSandwich = toppingAdder(startIterator.next(), currentSandwich, endIterator.previous())
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
    if (!isEmpty()) {
        val halfIndex = this.size / 2
        val bottomSliceIterator = listIterator()
        val topSliceIterator = listIterator(size)
        while (bottomSliceIterator.nextIndex() < halfIndex) {
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

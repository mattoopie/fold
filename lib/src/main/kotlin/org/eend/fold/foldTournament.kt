package org.eend.fold

/**
 * Repeatedly pairs neighbouring items into rounds and lets [battle] decide the winner.
 * If a round has an odd number of items, the final item advances automatically.
 */
inline fun <T> List<T>.foldTournament(
    battle: (left: T, right: T) -> T
): T? =
    foldTournamentIndexed { _, _, left, right, _ ->
        battle(left, right)
    }

/**
 * Repeatedly pairs neighbouring items into rounds and lets [battle] decide the winner.
 * If a round has an odd number of items, the final item advances automatically.
 * Also provides the round number and the indexes of the battling pair in that round.
 */
inline fun <T> List<T>.foldTournamentIndexed(
    battle: (round: Int, leftIndex: Int, left: T, right: T, rightIndex: Int) -> T
): T? {
    if (isEmpty()) {
        return null
    }

    var currentRound = this
    var round = 0
    while (currentRound.size > 1) {
        val nextRound = mutableListOf<T>()
        var index = 0
        while (index < currentRound.size) {
            if (index + 1 < currentRound.size) {
                nextRound += battle(round, index, currentRound[index], currentRound[index + 1], index + 1)
            } else {
                nextRound += currentRound[index]
            }
            index += 2
        }
        currentRound = nextRound
        round += 1
    }
    return currentRound.first()
}

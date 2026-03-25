package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoldTournamentKtTest {
    @Test
    fun `folding zero items returns null`() {
        val list = emptyList<Int>()

        assertThat(list.foldTournament { left, right -> maxOf(left, right) }).isNull()
    }

    @Test
    fun `folding one item returns that item`() {
        val list = listOf("winner")

        assertThat(list.foldTournament { left, right -> "$left$right" }).isEqualTo("winner")
    }

    @Test
    fun `folding in rounds uses battle winners in the next round`() {
        val list = listOf("a", "b", "c", "d")

        val winner = list.foldTournament { left: String, right: String ->
            "($left$right)"
        }

        assertThat(winner).isEqualTo("((ab)(cd))")
    }

    @Test
    fun `odd item advances automatically to the next round`() {
        val list = listOf("a", "b", "c", "d", "e")

        val winner = list.foldTournament { left: String, right: String ->
            "($left$right)"
        }

        assertThat(winner).isEqualTo("(((ab)(cd))e)")
    }

    @Test
    fun `indexed variant provides round-local indexes`() {
        val list = listOf("a", "b", "c", "d")

        val winner = list.foldTournamentIndexed { round: Int, leftIndex: Int, left: String, right: String, rightIndex: Int ->
            "$left$right@$round$leftIndex$rightIndex"
        }

        assertThat(winner).isEqualTo("ab@001cd@023@101")
    }
}

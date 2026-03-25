package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoldMitosisKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldResult = list.foldMitosis(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding one item returns that item`() {
        val list = listOf(1)
        val foldResult = list.foldMitosis(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("1")
    }

    @Test
    fun `folding splits the remaining traversal into queued halves`() {
        val list = listOf(1, 2, 3, 4, 5)
        val foldResult = list.foldMitosis(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("12435")
    }

    @Test
    fun `folding indexed variant reports mitosis traversal indexes`() {
        val list = ('a'..'e').toList()
        val foldResult = list.foldMitosisIndexed(INITIAL_VALUE) { index: Int, currentValue: String, _: Char ->
            "$currentValue$index"
        }

        assertThat(foldResult).isEqualTo("01324")
    }
}

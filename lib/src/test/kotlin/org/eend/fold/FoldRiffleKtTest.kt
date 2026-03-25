package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoldRiffleKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldResult = list.foldRiffle(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding even-sized list interleaves both halves`() {
        val list = listOf(1, 2, 3, 4, 5, 6)
        val foldResult = list.foldRiffle(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("142536")
    }

    @Test
    fun `folding odd-sized list lets the first half contribute the extra item`() {
        val list = listOf(1, 2, 3, 4, 5)
        val foldResult = list.foldRiffle(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("14253")
    }

    @Test
    fun `folding indexed variant provides riffle indexes`() {
        val list = ('a'..'f').toList()
        val foldResult = list.foldRiffleIndexed(INITIAL_VALUE) { index: Int, currentValue: String, _: Char ->
            "$currentValue$index"
        }

        assertThat(foldResult).isEqualTo("031425")
    }
}

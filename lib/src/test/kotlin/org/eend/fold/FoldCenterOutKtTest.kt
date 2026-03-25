package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoldCenterOutKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldResult = list.foldCenterOut(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding odd-sized list starts in the center and works outwards`() {
        val list = listOf(1, 2, 3, 4, 5)
        val foldResult = list.foldCenterOut(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("32415")
    }

    @Test
    fun `folding even-sized list starts with the left-center item`() {
        val list = listOf(1, 2, 3, 4)
        val foldResult = list.foldCenterOut(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("2314")
    }

    @Test
    fun `folding indexed variant provides center-out indexes`() {
        val list = ('a'..'e').toList()
        val foldResult = list.foldCenterOutIndexed(INITIAL_VALUE) { index: Int, currentValue: String, _: Char ->
            "$currentValue$index"
        }

        assertThat(foldResult).isEqualTo("21304")
    }
}

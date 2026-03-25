package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FoldPalindroneKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldResult = list.foldPalindrone(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding even-sized list revisits the first half in reverse`() {
        val list = listOf(1, 2, 3, 4)
        val foldResult = list.foldPalindrone(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("1221")
    }

    @Test
    fun `folding odd-sized list includes the center item in the palindrome`() {
        val list = listOf(1, 2, 3, 4, 5)
        val foldResult = list.foldPalindrone(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("123321")
    }

    @Test
    fun `folding indexed variant reports mirrored indexes`() {
        val list = ('a'..'e').toList()
        val foldResult = list.foldPalindroneIndexed(INITIAL_VALUE) { index: Int, currentValue: String, _: Char ->
            "$currentValue$index"
        }

        assertThat(foldResult).isEqualTo("012210")
    }
}

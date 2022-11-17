package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class FoldSandwichKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldedSandwich = list.foldSandwich(INITIAL_VALUE) { first: Int, currentValue: String, last: Int ->
            "$currentValue$first$last"
        }
        assertThat(foldedSandwich).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding one item returns initial value`() {
        val list = listOf(1)
        val foldedSandwich = list.foldSandwich(INITIAL_VALUE) { first: Int, currentValue: String, last: Int ->
            "$currentValue$first$last"
        }
        assertThat(foldedSandwich).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding two items returns initial value with first and second value added to that`() {
        val list = listOf(1, 2)
        val foldedSandwich = list.foldSandwich(INITIAL_VALUE) { first: Int, currentValue: String, last: Int ->
            "$currentValue$first$last"
        }

        val expectedSandwich = "12"
        assertThat(foldedSandwich).isEqualTo(expectedSandwich)
    }

    @Test
    fun `folding three items returns initial value with first and last value added to that`() {
        val list = listOf(1, 2, 3)
        val foldedSandwich = list.foldSandwich(INITIAL_VALUE) { first: Int, currentValue: String, last: Int ->
            "$currentValue$first$last"
        }

        val expectedSandwich = "13"
        assertThat(foldedSandwich).isEqualTo(expectedSandwich)
    }

    @Test
    fun `folding four items indexed provides correct indexes`() {
        val list = ('a'..'d').toList()
        val foldedSandwich =
            list.foldSandwichIndexed(INITIAL_VALUE) { firstIndex: Int,
                                                      _: Char,
                                                      currentValue: String,
                                                      _: Char,
                                                      lastIndex: Int ->
                "$currentValue$firstIndex$lastIndex"
            }

        val expectedSandwich = "0312"
        assertThat(foldedSandwich).isEqualTo(expectedSandwich)
    }


    @Test
    fun `folding five items indexed provides correct indexes`() {
        val list = ('a'..'e').toList()
        val foldedSandwich =
            list.foldSandwichIndexed(INITIAL_VALUE) { firstIndex: Int,
                                                      _: Char,
                                                      currentValue: String,
                                                      _: Char,
                                                      lastIndex: Int ->
                "$currentValue$firstIndex$lastIndex"
            }

        val expectedSandwich = "0413"
        assertThat(foldedSandwich).isEqualTo(expectedSandwich)
    }
}

package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class FoldZigzagKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldResult = list.foldZigzag(INITIAL_VALUE) { nextValue: Int, currentValue: String ->
            "$currentValue$nextValue"
        }
        assertThat(foldResult).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding one item returns first value`() {
        val list = listOf(1)
        val foldResult = list.foldZigzag(INITIAL_VALUE) { nextValue: Int, currentValue: String ->
            "$currentValue$nextValue"
        }
        assertThat(foldResult).isEqualTo("1")
    }

    @Test
    fun `folding two items returns initial value with first and second value added to that`() {
        val list = listOf(1, 2)
        val foldResult = list.foldZigzag(INITIAL_VALUE) { nextValue: Int, currentValue: String ->
            "$currentValue$nextValue"
        }

        val expectedResult = "12"
        assertThat(foldResult).isEqualTo(expectedResult)
    }

    @Test
    fun `folding three items returns initial value with first and last value added to that`() {
        val list = listOf(1, 2, 3)
        val foldResult = list.foldZigzag(INITIAL_VALUE) { nextValue: Int, currentValue: String ->
            "$currentValue$nextValue"
        }

        val expectedResult = "132"
        assertThat(foldResult).isEqualTo(expectedResult)
    }


    @Test
    fun `folding four items indexed provides correct indexes`() {
        val list = ('a'..'d').toList()
        val foldResult =
            list.foldZigzagIndexed(INITIAL_VALUE) { index: Int,
                                                    _: Char,
                                                    currentValue: String ->
                "$currentValue$index"
            }

        val expectedResult = "0312"
        assertThat(foldResult).isEqualTo(expectedResult)
    }


    @Test
    fun `folding five items indexed provides correct indexes`() {
        val list = ('a'..'e').toList()
        val foldResult =
            list.foldZigzagIndexed(INITIAL_VALUE) { index: Int,
                                                    _: Char,
                                                    currentValue: String ->
                "$currentValue$index"
            }

        val expectedResult = "04132"
        assertThat(foldResult).isEqualTo(expectedResult)
    }
}

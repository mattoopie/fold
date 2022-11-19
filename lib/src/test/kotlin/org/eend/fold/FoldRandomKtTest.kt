package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random


class FoldRandomKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    private val seededRandomInstance = Random(4)

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldResult = list.foldRandom(INITIAL_VALUE, seededRandomInstance) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }
        assertThat(foldResult).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `folding one item returns first value`() {
        val list = listOf(1)
        val foldResult = list.foldRandom(INITIAL_VALUE, seededRandomInstance) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }
        assertThat(foldResult).isEqualTo("1")
    }

    @Test
    fun `folding two items returns initial value with first and second value added to that`() {
        val list = listOf(1, 2)
        val foldResult = list.foldRandom(INITIAL_VALUE, seededRandomInstance) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        val expectedResult = "21"
        assertThat(foldResult).isEqualTo(expectedResult)
    }

    @Test
    fun `folding three items returns initial value with first and last value added to that`() {
        val list = listOf(1, 2, 3)
        val foldResult = list.foldRandom(INITIAL_VALUE, seededRandomInstance) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        val expectedResult = "231"
        assertThat(foldResult).isEqualTo(expectedResult)
    }


    @Test
    fun `folding four items indexed provides correct indexes`() {
        val list = ('a'..'d').toList()
        val foldResult =
            list.foldRandomIndexed(INITIAL_VALUE, seededRandomInstance) { index: Int,
                                                                          currentValue: String,
                                                                          _: Char ->
                "$currentValue$index"
            }

        val expectedResult = "0123"
        assertThat(foldResult).isEqualTo(expectedResult)
    }


    @Test
    fun `folding five items indexed provides correct indexes`() {
        val list = ('a'..'e').toList()
        val foldResult =
            list.foldRandomIndexed(INITIAL_VALUE, seededRandomInstance) { index: Int,
                                                                          currentValue: String,
                                                                          _: Char ->
                "$currentValue$index"
            }

        val expectedResult = "01234"
        assertThat(foldResult).isEqualTo(expectedResult)
    }
}

package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import kotlin.random.Random

class FoldRussianRouletteKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding zero items returns initial value`() {
        val list = emptyList<Int>()
        val foldResult = list.foldRussianRoulette(INITIAL_VALUE) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo(INITIAL_VALUE)
    }

    @Test
    fun `chance zero keeps every item`() {
        val list = listOf(1, 2, 3, 4)
        val foldResult = list.foldRussianRoulette(INITIAL_VALUE, 0f, Random.Default) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("1234")
    }

    @Test
    fun `items can disappear before being visited`() {
        val list = listOf(1, 2, 3, 4)
        val randomInstance = FloatSequenceRandom(0.1f, 0.8f, 0.2f, 0.7f)

        val foldResult = list.foldRussianRoulette(INITIAL_VALUE, 0.5f, randomInstance) { currentValue: String, nextValue: Int ->
            "$currentValue$nextValue"
        }

        assertThat(foldResult).isEqualTo("24")
    }

    @Test
    fun `indexed variant reports surviving original indexes`() {
        val list = listOf('a', 'b', 'c', 'd')
        val randomInstance = FloatSequenceRandom(0.1f, 0.8f, 0.2f, 0.7f)

        val foldResult = list.foldRussianRouletteIndexed(INITIAL_VALUE, 0.5f, randomInstance) {
                index: Int,
                currentValue: String,
                _: Char ->
            "$currentValue$index"
        }

        assertThat(foldResult).isEqualTo("13")
    }

    @Test
    fun `invalid disappearance chance throws`() {
        assertThatThrownBy {
            listOf(1, 2, 3).foldRussianRoulette(INITIAL_VALUE, 1.5f) { currentValue: String, nextValue: Int ->
                "$currentValue$nextValue"
            }
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("chanceOfDisappearing must be between 0 and 1")
    }

    private class FloatSequenceRandom(vararg floats: Float) : Random() {
        private val values = ArrayDeque(floats.toList())

        override fun nextBits(bitCount: Int): Int = 0

        override fun nextFloat(): Float = values.removeFirst()
    }
}

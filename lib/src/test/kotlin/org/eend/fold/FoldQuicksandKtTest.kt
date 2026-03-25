package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.TestTimeSource

class FoldQuicksandKtTest {
    companion object {
        private const val INITIAL_VALUE = ""
    }

    @Test
    fun `folding with no delay keeps the output intact`() {
        val timeSource = TestTimeSource()
        val list = listOf("Q", "u", "i", "c", "k")

        val foldResult = list.foldQuicksand(INITIAL_VALUE, Random(4), timeSource) { currentValue: String, nextValue: String ->
            currentValue + nextValue
        }

        assertThat(foldResult).isEqualTo("Quick")
    }

    @Test
    fun `slower transforms garble more of the output`() {
        val expectedResult = "Quicks"

        val shortTimeSource = TestTimeSource()
        val shortResult = listOf("Q", "u", "i", "c", "k", "s").foldQuicksand(INITIAL_VALUE, Random(7), shortTimeSource) {
                currentValue: String,
                nextValue: String ->
            shortTimeSource += 5.milliseconds
            currentValue + nextValue
        }

        val longTimeSource = TestTimeSource()
        val longResult = listOf("Q", "u", "i", "c", "k", "s").foldQuicksand(INITIAL_VALUE, Random(7), longTimeSource) {
                currentValue: String,
                nextValue: String ->
            longTimeSource += 25.milliseconds
            currentValue + nextValue
        }

        assertThat(countGarbleDifferences(expectedResult, shortResult)).isEqualTo(2)
        assertThat(countGarbleDifferences(expectedResult, longResult)).isEqualTo(4)
    }

    @Test
    fun `indexed variant provides the original traversal indexes`() {
        val timeSource = TestTimeSource()
        val list = ('a'..'e').toList()

        val foldResult = list.foldQuicksandIndexed(INITIAL_VALUE, Random(4), timeSource) { index: Int, currentValue: String, _: Char ->
            "$currentValue$index"
        }

        assertThat(foldResult).isEqualTo("01234")
    }

    private fun countGarbleDifferences(expected: String, actual: String): Int =
        expected.zip(actual).count { (expectedChar, actualChar) ->
            expectedChar != actualChar
        }
}

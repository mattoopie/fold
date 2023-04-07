package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

class FoldChaosKtTest {
    @Test
    fun `chaos is introduced to the input`() {
        val list = listOf(1, 2, "App", 0.55f, true, 5, "Fiets", listOf("Kotlin", "Java", "Rust"))
        val randomInstance = Random(6)
        val result = list.foldChaos("", randomInstance) { acc, next ->
            acc + next
        }
        assertThat(result).isEqualTo("1-9.5383296E74.90477088E8truefalsetrueApp")
    }
}

package org.eend.fold

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.random.Random

class FoldChaosKtTest {
    @Test
    fun `chaos is introduced to the input`() {
        val list = listOf(
            1,
            2,
            "App",
            0.55f,
            true,
            "Fiets",
            listOf("Kotlin", "Java", "Rust"),
            object : Any() {
                override fun toString(): String = "Some function value"
            }
        )
        val randomInstance = Random(18)
        val result = list.foldChaos("", randomInstance) { acc, next ->
            acc + next
        }
        val expectedResult =
            "App[Kotlin, Java, Rust]-3.47251328E82Some function value[Java, hW4pD6sywM, Rust, Rust, Rust]Apptrue"
        assertThat(result).isEqualTo(expectedResult)
    }
}

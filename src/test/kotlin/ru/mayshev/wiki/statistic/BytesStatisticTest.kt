package ru.mayshev.wiki.statistic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class BytesStatisticTest {
    @Test
    fun simple() {
        testBytes(
            listOf(
                "1" to "1"
            ),
            "65"
        )
    }

    @Test
    fun second() {
        testBytes(
            listOf(
                "1" to "2",
                "2" to "0",
                "3" to "1",
            ),
            "65",
            "49",
            "1256",
        )
    }

    @Test
    fun big() {
        testBytes(
            listOf(
                "1" to "1",
                "2" to "1",
                "3" to "3",
                "4" to "7",
                "5" to "12"
            ),
            "49",
            "1256",
            "115337",
            "539230",
            "72184",
            "106861",
            "24683",
            "124883",
            "52222",
            "44300",
            "136263",
            "327",
            "6596",
            "2474",
            "267289",
            "51053",
            "339419",
            "96394",
            "78419",
            "106436",
            "376583",
            "294971",
            "113557",
            "267041"
        )
    }

    private fun testBytes(expectedStatistics: List<Pair<String, String>>, vararg strings: String) {
        val bytesStatistic = BytesStatistic().apply {
            for (data in strings) {
                update(data)
            }
        }

        Assertions.assertEquals(expectedStatistics, bytesStatistic.collect())
    }
}

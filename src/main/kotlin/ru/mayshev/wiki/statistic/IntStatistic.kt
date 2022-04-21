package ru.mayshev.wiki.statistic

import java.util.concurrent.ConcurrentHashMap

abstract class IntStatistic : Statistic {
    private val storage = ConcurrentHashMap<Int, Int>()

    override fun update(data: String) {
        storage.compute(getIndex(data)) { _, value ->
            value?.plus(1) ?: 1
        }
    }

    override fun collect(): List<Pair<String, String>> = storage.keys.minMaxRange().map { index ->
        index.toString() to "${storage[index] ?: 0}"
    }

    protected abstract fun getIndex(data: String): Int

    // range between minimum and maximum values
    private fun Iterable<Int>.minMaxRange(): IntRange {
        val minIndex = minOrNull() ?: return IntRange.EMPTY
        val maxIndex = maxOrNull() ?: return IntRange.EMPTY

        return minIndex..maxIndex
    }
}

package ru.mayshev.wiki.handler

import ru.mayshev.wiki.ConcurrentExecutor
import ru.mayshev.wiki.statistic.BytesStatistic
import ru.mayshev.wiki.statistic.FrequencyStatistic
import ru.mayshev.wiki.statistic.TimestampStatistic

class StatisticsCollector(numberOfThreads: Int) : ConcurrentExecutor(numberOfThreads) {
    private val _statistics = mapOf(
        "title"     to FrequencyStatistic(),
        "text"      to FrequencyStatistic(),
        "bytes"     to BytesStatistic(),
        "timestamp" to TimestampStatistic(),
    )

    val statistics: Map<String, List<Pair<String, String>>> get() = _statistics.mapValues { (_, statistic) ->
        statistic.collect()
    }

    fun addContent(content: Content) = execute {
        _statistics.forEach { (name, statistic) ->
            statistic.update(content.getValue(name))
        }
    }
}

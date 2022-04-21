package ru.mayshev.wiki.statistic

import java.util.*
import java.util.concurrent.ConcurrentHashMap

class FrequencyStatistic : Statistic {
    companion object {
        private const val TAKE_FIRST = 300
    }

    private val storage = ConcurrentHashMap<String, Int>()
    private val regex = "[а-яA-Z]{3,}".toRegex()

    override fun update(data: String) = regex.findAll(data.lowercase(Locale.getDefault())).forEach {
        storage.compute(it.value) { _, frequency ->
            frequency?.plus(1) ?: 1
        }
    }

    override fun collect(): List<Pair<String, String>> {
        return storage.toList().sortedWith(compareByDescending<Pair<String, Int>> { (_, frequency) ->
            frequency
        }.thenBy { (word, _) ->
            word
        }).take(TAKE_FIRST).map { (word, frequency) ->
            frequency.toString() to word
        }
    }
}

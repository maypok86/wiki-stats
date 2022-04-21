package ru.mayshev.wiki.statistic

interface Statistic {
    fun update(data: String)
    fun collect(): List<Pair<String, String>>
}

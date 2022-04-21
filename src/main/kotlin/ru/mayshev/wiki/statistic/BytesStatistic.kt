package ru.mayshev.wiki.statistic

class BytesStatistic : IntStatistic() {
    override fun getIndex(data: String): Int =
        data.toIntOrNull()?.run { data.lastIndex } ?: throw Exception("Wrong bytes size in text block in *.bz2 file")
}

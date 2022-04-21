package ru.mayshev.wiki.statistic

class TimestampStatistic : IntStatistic() {
    companion object {
        private const val YEAR_LENGTH = 4
    }

    override fun getIndex(data: String): Int =
        data.take(YEAR_LENGTH).toIntOrNull() ?: throw Exception("Wrong year format in timestamp block in *.bz2 file")
}

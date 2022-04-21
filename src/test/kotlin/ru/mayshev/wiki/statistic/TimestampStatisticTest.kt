package ru.mayshev.wiki.statistic

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TimestampStatisticTest {
    @Test
    fun simple() {
        testTimestamp(
            listOf(
                "2020" to "1"
            ),
            "2020-01-01T00:00:00Z"
        )
    }

    @Test
    fun second() {
        testTimestamp(
            listOf(
                "2004" to "1",
                "2005" to "0",
                "2006" to "0",
                "2007" to "0",
                "2008" to "0",
                "2009" to "0",
                "2010" to "0",
                "2011" to "0",
                "2012" to "0",
                "2013" to "0",
                "2014" to "0",
                "2015" to "0",
                "2016" to "0",
                "2017" to "0",
                "2018" to "1",
                "2019" to "0",
                "2020" to "1"
            ),
            "2020-01-01T00:00:00Z",
            "2004-08-09T04:36:57Z",
            "2018-11-02T13:25:41Z",
        )
    }

    @Test
    fun big() {
        testTimestamp(
            listOf(
                "2004" to "1",
                "2005" to "0",
                "2006" to "0",
                "2007" to "0",
                "2008" to "0",
                "2009" to "0",
                "2010" to "0",
                "2011" to "0",
                "2012" to "0",
                "2013" to "0",
                "2014" to "0",
                "2015" to "0",
                "2016" to "0",
                "2017" to "0",
                "2018" to "2",
                "2019" to "1",
                "2020" to "20"
            ),
            "2004-08-09T04:36:57Z",
            "2018-11-02T13:25:41Z",
            "2020-10-28T22:08:35Z",
            "2020-10-31T01:38:02Z",
            "2020-10-31T14:30:17Z",
            "2020-09-27T12:38:13Z",
            "2020-10-08T18:17:54Z",
            "2020-10-29T08:51:36Z",
            "2020-10-10T22:58:11Z",
            "2020-09-01T17:37:28Z",
            "2020-09-27T18:02:42Z",
            "2020-10-22T01:04:40Z",
            "2018-01-19T13:37:58Z",
            "2019-05-21T21:46:39Z",
            "2020-10-24T18:02:12Z",
            "2020-10-06T16:19:08Z",
            "2020-10-30T17:28:46Z",
            "2020-10-30T07:33:15Z",
            "2020-10-23T17:37:36Z",
            "2020-10-10T23:23:47Z",
            "2020-07-28T17:52:46Z",
            "2020-10-21T15:38:34Z",
            "2020-10-26T10:07:01Z",
            "2020-10-31T19:14:29Z"
        )
    }

    private fun testTimestamp(expectedStatistics: List<Pair<String, String>>, vararg strings: String) {
        val timestampStatistic = TimestampStatistic().apply {
            for (data in strings) {
                update(data)
            }
        }

        Assertions.assertEquals(expectedStatistics, timestampStatistic.collect())
    }
}

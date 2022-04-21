package ru.mayshev.wiki.handler

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class WikiHandler(numberOfThreads: Int) : DefaultHandler() {
    companion object {
        private const val PAGE_TAG = "page"
        private const val TITLE_TAG = "title"
        private const val REVISION_TAG = "revision"
        private const val TEXT_TAG = "text"
        private const val TIMESTAMP_TAG = "timestamp"
        private const val BYTES_ATTRIBUTE = "bytes"
        private const val LINE_BREAK = "\n"

        private val parentTags = mapOf(
            // expected paths
            TITLE_TAG       to listOf(PAGE_TAG, TITLE_TAG),
            TEXT_TAG        to listOf(PAGE_TAG, REVISION_TAG, TEXT_TAG),
            TIMESTAMP_TAG   to listOf(PAGE_TAG, REVISION_TAG, TIMESTAMP_TAG),
        )
        private val parentAttributes = mapOf(
            BYTES_ATTRIBUTE to listOf(PAGE_TAG, REVISION_TAG, TEXT_TAG),
        )
        private val tagInfo = listOf(
            TITLE_TAG       to "Топ-300 слов в заголовках статей:",
            TEXT_TAG        to "Топ-300 слов в статьях:",
            BYTES_ATTRIBUTE to "Распределение статей по размеру:",
            TIMESTAMP_TAG   to "Распределение статей по времени:",
        )
    }

    private var content = Content()

    private val actualPath = mutableListOf<String>()
    private val statisticsCollector = StatisticsCollector(numberOfThreads)

    val statistics: String get() {
        val statisticsMap = statisticsCollector.statistics

        return tagInfo.joinToString(LINE_BREAK) { (name, info) ->
            info + LINE_BREAK + statisticsMap[name]?.joinToString("") { (index, value) ->
                "$index $value$LINE_BREAK"
            }
        }
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        if (qName == null) {
            return
        }

        actualPath.add(qName)
        // bytes attribute
        parentAttributes.forEach { (attribute, expectedPath) ->
            if (actualPath.takeLast(expectedPath.size) == expectedPath) {
                val data = attributes?.getValue(attribute) ?: run {
                    resetContent()

                    return
                }

                content.addValue(attribute, data.toCharArray())

                if (data == "0") {
                    content.addValue(expectedPath.last(), CharArray(0)) // add empty text
                }
            }
        }
    }

    override fun characters(chars: CharArray, start: Int, length: Int) {
        actualPath.lastOrNull()?.let { tag ->
            parentTags[tag]?.let { expectedPath ->
                if (actualPath.takeLast(expectedPath.size) == expectedPath) {
                    content.addValue(tag, chars.sliceArray(start until start + length))
                }
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if (qName == PAGE_TAG) {
            if (content.isFull()) {
                statisticsCollector.addContent(content)
            }

            resetContent()
        }

        actualPath.removeLastOrNull() // ignore if empty
    }

    private fun resetContent() {
        content = Content()
        actualPath.clear()
    }

    fun shutdown() = statisticsCollector.shutdown()
}

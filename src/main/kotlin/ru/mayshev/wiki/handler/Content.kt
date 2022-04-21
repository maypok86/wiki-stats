package ru.mayshev.wiki.handler

class Content {
    private val data = mapOf(
        "title"     to StringBuilder(),
        "text"      to StringBuilder(),
        "bytes"     to StringBuilder(),
        "timestamp" to StringBuilder()
    )

    fun getValue(name: String): String = data[name]?.toString() ?: throw Exception("Text by $name doesn't exist")

    fun addValue(name: String, chars: CharArray) {
        data[name]?.append(chars)
    }

    fun isFull(): Boolean = data.all { (_, sb) -> sb.isNotEmpty() }
}

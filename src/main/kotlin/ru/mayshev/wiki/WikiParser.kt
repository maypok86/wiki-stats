package ru.mayshev.wiki

import ru.mayshev.wiki.handler.WikiHandler
import java.io.File
import javax.xml.parsers.SAXParserFactory

class WikiParser(numberOfThreads: Int) {
    private val parser = SAXParserFactory.newInstance().newSAXParser()
    private val handler = WikiHandler(numberOfThreads)

    fun parse(inputs: Iterable<File>): String {
        inputs.forEach { file ->
            parser.parse(file, handler)
        }
        handler.shutdown()

        return handler.statistics
    }
}

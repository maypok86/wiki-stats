package ru.mayshev.wiki

import java.io.File
import kotlin.math.min

private const val TEMP_DIR = "temp_data"

fun solve(parameters: Parameters) {
    parameters.inputs.takeIf {
        it.all { file ->
            file.extension == "bz2"
        }
    } ?: throw Exception("Arguments do not contain a *.bz2 file.")

    val root = File(TEMP_DIR).apply {
        if (!exists()) {
            mkdirs()
        }
    }
    val inputs = parameters.inputs.map { file ->
        File("$TEMP_DIR/${file.nameWithoutExtension}.xml")
    }
    val archiver = Bzip2Archiver(min(parameters.threads - 1, parameters.inputs.size))

    inputs.forEachIndexed { index, file ->
        archiver.unbzip2(parameters.inputs[index], file)
    }
    archiver.shutdown()

    val statistics = WikiParser(parameters.threads - 1).parse(inputs)

    File(parameters.output).bufferedWriter().use {
        it.write(statistics)
    }

    root.deleteRecursively()
}

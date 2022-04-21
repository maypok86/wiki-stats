package ru.mayshev.wiki

import net.sf.sevenzipjbinding.ArchiveFormat
import net.sf.sevenzipjbinding.SevenZip
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream
import java.io.File
import java.io.RandomAccessFile

class Bzip2Archiver(numberOfThreads: Int) : ConcurrentExecutor(numberOfThreads) {
    init {
        SevenZip.initSevenZipFromPlatformJAR()
    }

    fun unbzip2(input: File, output: File) = execute {
        unarchive(input, output)
    }

    private fun unarchive(inputFile: File, outputFile: File) {
        val inArchive = SevenZip.openInArchive(
            ArchiveFormat.BZIP2,
            RandomAccessFileInStream(RandomAccessFile(inputFile, "r"))
        )
        val input = inArchive.simpleInterface.archiveItems
        val output = outputFile.outputStream()

        input.single().extractSlow { data ->
            output.write(data)
            data.size
        }

        inArchive.close()
        output.close()
    }
}

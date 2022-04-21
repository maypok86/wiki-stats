package ru.mayshev.wiki

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

abstract class ConcurrentExecutor(numberOfThreads: Int) {
    private val threadPool = if (numberOfThreads >= 1) {
        Executors.newFixedThreadPool(numberOfThreads)
    } else {
        null
    }

    protected fun execute(lambda: () -> Unit) {
        threadPool?.let { threadPool ->
            threadPool.submit {
                lambda()
            }
        } ?: lambda()
    }

    fun shutdown() {
        threadPool?.let { threadPool ->
            threadPool.shutdown()

            if (!threadPool.awaitTermination(1, TimeUnit.MINUTES)) {
                threadPool.shutdownNow()
            }
        }
    }
}

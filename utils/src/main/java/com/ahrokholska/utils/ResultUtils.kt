package com.ahrokholska.utils

import kotlin.coroutines.cancellation.CancellationException

object ResultUtils {
    suspend fun <T> getResult(block: suspend () -> T) = try {
        Result.success(block())
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.failure(e)
    }
}
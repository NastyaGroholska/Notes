package com.ahrokholska.notes.data.work

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ahrokholska.api.NotesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@HiltWorker
class CopyImageWorker @AssistedInject constructor(
    private val repository: NotesRepository,
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters
) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val file = File(
            appContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "$IMAGE_PATH/${System.currentTimeMillis()}.jpg"
        )
        if (file.parentFile?.exists() == false) {
            if (file.parentFile?.mkdirs() == false) {
                return@withContext Result.failure()
            }
        }
        val resolver = applicationContext.contentResolver
        val uriString = inputData.getString(URI_PARAM) ?: return@withContext Result.failure()
        val uri = Uri.parse(uriString)
        FileOutputStream(file).use { output ->
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(resolver, uri)
            } else {
                val source = ImageDecoder.createSource(resolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output)
        }
        repository.updateImage(uriString, file.path)
        Result.success()
    }

    companion object {
        const val IMAGE_PATH = "images"
        const val URI_PARAM = "uri"
    }
}
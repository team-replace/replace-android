package com.app.replace.ui.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream

fun processAndAdjustImage(context: Context, uri: Uri): File {
    val bitmap = uri.getBitmapFromUri(context)
    val file = bitmap?.convertResizeImage(context)
    file?.updateExifOrientation(context, uri)
    return file ?: File("")
}

private fun Uri.getBitmapFromUri(context: Context): Bitmap? {
    return context.contentResolver.openInputStream(this)?.use {
        BitmapFactory.decodeStream(it)
    }
}

private fun Bitmap.convertResizeImage(context: Context): File {
    val tempFile = File.createTempFile("resized_image", ".jpg", context.cacheDir)

    FileOutputStream(tempFile).use { fileOutputStream ->
        this.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream)
    }
    return tempFile
}

private fun File.updateExifOrientation(context: Context, uri: Uri) {
    context.contentResolver.openInputStream(uri)?.use {
        val exif = ExifInterface(it)
        exif.getAttribute(ExifInterface.TAG_ORIENTATION)?.let { attribute ->
            val newExif = ExifInterface(absolutePath)
            newExif.setAttribute(ExifInterface.TAG_ORIENTATION, attribute)
            newExif.saveAttributes()
        }
    }
}

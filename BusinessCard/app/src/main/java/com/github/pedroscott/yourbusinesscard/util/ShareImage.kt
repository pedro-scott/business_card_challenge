package com.github.pedroscott.yourbusinesscard.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import com.github.pedroscott.yourbusinesscard.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object ShareImage {

    fun share(context: Context, view: View, filename: String = "") {
        val bitmap = getScreenShotFromView(view)

        bitmap?.let {
            shareMedia(context, bitmap, filename)
        }
    }

    private fun getScreenShotFromView(view: View): Bitmap? {
        var screenshot: Bitmap? = null

        try {
            screenshot = Bitmap.createBitmap(
                view.measuredWidth,
                view.measuredHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(screenshot)

            view.draw(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return screenshot
    }

    private fun shareMedia(context: Context, bitmap: Bitmap, filename: String) {
        val fName =
            if (filename == "")
                "${System.currentTimeMillis()}.jpg"
            else
                filename

        var outputStream: OutputStream?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.also { resolver ->

                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                outputStream = imageUri?.let { uri ->
                    shareIntent(context, imageUri)
                    resolver.openOutputStream(uri)
                }

            }
        } else {
            val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, fName)

            shareIntent(context, Uri.fromFile(image))

            outputStream = FileOutputStream(image)
        }

        outputStream?.use { outStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        }
    }

    private fun shareIntent(context: Context, imageUri: Uri) {
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUri)
            type = "image/jpeg"
        }.let { intent ->
            context.startActivity(
                Intent.createChooser(
                    intent,
                    context.getString(R.string.title_share_card)
                )
            )
        }
    }

}
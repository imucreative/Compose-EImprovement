package com.fastrata.eimprovement.utils

import android.provider.OpenableColumns
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.content.ContentUris
import android.os.Build
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import java.text.CharacterIterator
import java.text.StringCharacterIterator
import kotlin.math.abs
import android.annotation.TargetApi

class FileInformation {
    /**
     * resource : https://gist.github.com/VassilisPallas/b88fb701c55cdace0c420356ee7c1464
     *
     * Method for return file path of Gallery image/ Document / Video / Audio
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri
     * @return path of the selected image file from gallery
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun getPath(context: Context?, uri: Uri): String? {
        val isLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

        // DocumentProvider
        if (isLollipop && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                return getDataColumn(context!!, contentUri, null, null)
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context!!, contentUri, selection, selectionArgs)
            }
        }
        // MediaStore (and general)
        else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.lastPathSegment

            return getDataColumn(context!!, uri, null, null)
        }
        // File
        else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return "unknown"
    }

    fun getName(context: Context, uri: Uri?): String? {
        var fileName: String? = null
        val cursor: Cursor? = uri?.let {
            context.contentResolver
                .query(it, null, null, null, null, null)
        }
        cursor.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                // get file name
                fileName = cursor.getString(
                    cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                )
            }
        }
        return fileName
    }

    fun getSize(context: Context, uri: Uri?): String? {
        var fileSize: String? = null
        val cursor: Cursor? = uri?.let {
            context.contentResolver.query(
                it,
                null,
                null,
                null,
                null,
                null
            )
        }
        cursor.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {

                // get file size
                val sizeIndex: Int = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (!cursor.isNull(sizeIndex)) {
                    fileSize = cursor.getString(sizeIndex)
                }
            }
        }
        return fileSize
    }

    // https://stackoverflow.com/a/3758880
    private fun humanReadableByteCountBin(bytes: Long): String {
        val absB = if (bytes == Long.MIN_VALUE) Long.MAX_VALUE else abs(bytes)
        if (absB < 1024) {
            return "$bytes B"
        }
        var value = absB
        val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
        var i = 40
        while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
            value = value shr 10
            ci.next()
            i -= 10
        }
        value *= java.lang.Long.signum(bytes).toLong()
        return String.format("%.1f %cB", value / 1024.0, ci.current())
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = uri?.let {
                context.contentResolver.query(
                    it, projection, selection, selectionArgs, null
                )
            }
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri
     * The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}
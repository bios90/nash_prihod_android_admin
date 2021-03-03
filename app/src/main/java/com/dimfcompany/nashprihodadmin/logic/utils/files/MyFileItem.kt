package com.dimfcompany.nashprihodadmin.logic.utils.files

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.CancellationSignal
import android.util.Log
import android.util.Size
import com.dimfcompany.nashprihodadmin.base.ObjWithFile
import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.ObjWithVideo
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import okhttp3.MultipartBody
import java.io.File
import java.io.Serializable
import java.util.*


class MyFileItem(private var uri: String) : Serializable, ObjWithImageUrl, ObjWithFile, ObjWithVideo, ObjWithMedia
{
    companion object
    {
        fun createFromData(data: Uri): MyFileItem
        {
            val file: File

            if (FileManager.isContentImage(data))
            {
                val extension = FileManager.getExtensionFromContentUri(data)
                file = FileManager.create_temp_image_file(extension)
            }
            else if (FileManager.isContentPdf(data))
            {
                file = FileManager.create_temp_image_file("pdf")
            }
            else
            {
                val extension = FileManager.getExtensionFromContentUri(data) ?: ""
                file = FileManager.create_temp_image_file(extension)
            }

            FileManager.copyToFileFromIntentData(file, data)
            val myFile = MyFileItem(file.absolutePath)
            myFile.original_name = FileManager.getUriFileName(data)
            myFile.type = FileManager.getMyTypeFromUri(data)
            return myFile
        }

        fun createFromFile(file: File): MyFileItem
        {
            return MyFileItem(file.absolutePath)
        }
    }

    var original_name: String? = null

    override var type: TypeMedia? = null
    override var preview_url: String? = uri

    override var image_url = uri

    override val video_url = uri
    override val video_preview_url = uri

    fun getUri(): Uri
    {
        return Uri.parse(uri)
    }

    fun getUriAsString(): String
    {
        return uri
    }

    fun getFile(): File
    {
        return File(getUri().path)
    }

    fun isImage(): Boolean
    {
        return FileManager.isFileImage(uri)
    }

    //Do not forget to add FLAG_GRANT_READ_URI_PERMISSION to both chooser and intent
    fun getUriForShare(): Uri
    {
        return FileManager.uriFromFile(getFile())
    }

    fun toMultiPartData(field_name: String = "file", media_type: String = "multipart/form-data"): MultipartBody.Part?
    {
        return getFile().toPartBody(field_name)
    }

    fun getSizeInMb(): Double
    {
        return getFile().length().toDouble() / (1024 * 1024).toDouble()
    }

    fun getFileName(): String
    {
        return getFile().name
    }

    override fun getObjFileName(): String
    {
        return original_name ?: getFileName()
    }

    override fun getObjFileSize(): Double
    {
        return getSizeInMb()
    }
}



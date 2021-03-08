package com.dimfcompany.nashprihodadmin.logic.models

import android.util.Log
import com.dimfcompany.nashprihodadmin.base.*
import com.dimfcompany.nashprihodadmin.base.enums.TypeFile
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ModelFile
    (
        override var id: Long?,
        @SerializedName("created_at")
        override var created: Date?,
        @SerializedName("updated_at")
        override var updated: Date?,
        @SerializedName("deleted_at")
        override var deleted: Date?,
        var file_name: String? = null,
        var file_original_name: String? = null,
        var file_mime_type: String? = null,
        var file_size: Long? = null,
        @SerializedName("file_type")
        var server_file_type: TypeFile?,
        var url: String? = null,
        var preview_image: ModelFile? = null
) : Serializable, ObjectWithId, ObjectWithDates, ObjWithMedia, ObjWithImageUrl, ObjWithVideo
{
    override val image_url: String?
        get()
        {
            return url
        }

    override val video_url: String?
        get()
        {
            return url
        }
    override val video_preview_url: String?
        get()
        {
            return preview_image?.image_url
        }

    override var type: TypeMedia?
        get()
        {
            return this.server_file_type?.toTypeMedia()
        }
        set(value)
        {
        }

    override var preview_url: String?
        get()
        {
            if (server_file_type == TypeFile.VIDEO)
            {
                return preview_image?.image_url
            }
            else
            {
                return url
            }
        }
        set(value)
        {
        }
}
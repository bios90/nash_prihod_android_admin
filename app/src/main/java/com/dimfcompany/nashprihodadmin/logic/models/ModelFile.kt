package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.ObjWithFile
import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.ObjectWithDates
import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import com.dimfcompany.nashprihodadmin.base.enums.TypeFile
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
        var type: TypeFile,
        var url: String? = null,
        var preview_image: ModelFile? = null
) : Serializable, ObjectWithId, ObjectWithDates, ObjWithImageUrl
{
    override val image_url: String?
        get() = url
}
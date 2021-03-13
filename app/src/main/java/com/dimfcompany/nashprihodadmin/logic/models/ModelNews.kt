package com.dimfcompany.nashprihodadmin.logic.models

import android.util.Log
import com.dimfcompany.nashprihodadmin.base.AppClass
import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.ObjectWithDates
import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import com.dimfcompany.nashprihodadmin.base.enums.TypeNews
import com.dimfcompany.nashprihodadmin.base.extensions.toObjOrNullGson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.Exception
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class ModelNews(
        override var id: Long? = null,
        var title: String? = null,
        var text: String? = null,
        var type: TypeNews? = null,
        @SerializedName("medias")
        var media_objs: ArrayList<ObjWithMedia>? = arrayListOf(),
        val author: ModelUser? = null,
        @SerializedName("created_at")
        override var created: Date? = null,
        @SerializedName("updated_at")
        override var updated: Date? = null,
        @SerializedName("deleted_at")
        override var deleted: Date? = null,
) : Serializable, ObjectWithId, ObjectWithDates

class ObjWithMediaDeserializer : JsonDeserializer<ObjWithMedia>
{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObjWithMedia?
    {
        val json_object = json?.asJsonObject
        return context?.deserialize(json_object, ModelFile::class.java)
    }
}

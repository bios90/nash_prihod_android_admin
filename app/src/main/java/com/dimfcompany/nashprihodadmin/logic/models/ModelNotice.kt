package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.ObjectWithDates
import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import com.dimfcompany.nashprihodadmin.base.enums.TypeNews
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class ModelNotice(
        override var id: Long? = null,
        var title: String? = null,
        var text: String? = null,
        val author: ModelUser? = null,
        @SerializedName("created_at")
        override var created: Date? = null,
        @SerializedName("updated_at")
        override var updated: Date? = null,
        @SerializedName("deleted_at")
        override var deleted: Date? = null,
) : Serializable, ObjectWithId, ObjectWithDates
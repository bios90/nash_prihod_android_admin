package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.ObjectWithDates
import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class ModelService(
        override var id: Long? = null,
        @SerializedName("created_at")
        override var created: Date? = null,
        @SerializedName("updated_at")
        override var updated: Date? = null,
        @SerializedName("deleted_at")
        override var deleted: Date? = null,
        var title: String? = null,
        var date: Date? = null,
        var service_times: ArrayList<ModelServiceTime>? = null,
        var service_texts: ArrayList<ModelServiceText>? = null,
        var author: ModelUser? = null
) : ObjectWithId, ObjectWithDates
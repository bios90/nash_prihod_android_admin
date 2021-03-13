package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.ObjectWithDates
import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import com.google.gson.annotations.SerializedName
import java.util.*

class ModelUser(
        override var id: Long? = null,
        @SerializedName("created_at")
        override var created: Date? = null,
        @SerializedName("updated_at")
        override var updated: Date? = null,
        @SerializedName("deleted_at")
        override var deleted: Date? = null,
        var first_name: String? = null,
        var last_name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var birthday: Date? = null,
        var about_me: String? = null,
        var status: TypeUserStatus? = null,
        var avatar: ModelFile? = null,
        var is_admin: Boolean? = false,
) : ObjectWithId, ObjectWithDates
{
    fun getFullName(): String
    {
        return "$last_name $first_name"
    }
}
package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.AppClass
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

    companion object
    {
        fun getTestUser():ModelUser
        {
            val user_str = "{\"avatar\":{\"created_at\":\"2021-03-09 22:11:24\",\"file_mime_type\":\"image/jpeg\",\"file_name\":\"53dd636940d5a88691d4b7cd7617379d4e81b281.jpg\",\"file_size\":5523462,\"id\":1,\"file_type\":\"image\",\"updated_at\":\"2021-03-09 22:11:24\",\"url\":\"https://i.pravatar.cc/324\"},\"created_at\":\"2021-03-09 22:11:24\",\"email\":\"Bios90@mail.ru\",\"first_name\":\"Филипп\",\"id\":1,\"is_admin\":true,\"last_name\":\"Бесядовский\",\"updated_at\":\"2021-03-10 01:15:12\"}"
            val user = AppClass.gson.fromJson(user_str,ModelUser::class.java)
            return user
        }
    }
}
package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.google.gson.annotations.SerializedName

class RespUserSingle
    (
        @SerializedName("data")
        val user: ModelUser? = null
) : BaseResponse()
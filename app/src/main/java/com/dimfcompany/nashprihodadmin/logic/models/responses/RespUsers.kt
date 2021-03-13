package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.google.gson.annotations.SerializedName

class RespUsers
    (
        @SerializedName("data")
        val users: ArrayList<ModelUser>? = null
) : BaseResponse()
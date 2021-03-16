package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.google.gson.annotations.SerializedName

class RespServices
    (
        @SerializedName("data")
        val services: ArrayList<ModelService>? = null
) : BaseResponse()
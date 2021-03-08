package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.google.gson.annotations.SerializedName

class RespNews
    (
        @SerializedName("data")
        val news: ArrayList<ModelNews>? = null
) : BaseResponse()
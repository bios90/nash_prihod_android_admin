package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.google.gson.annotations.SerializedName

class RespNoticeSingle
    (
        @SerializedName("data")
        val notice: ModelNotice? = null
) : BaseResponse()
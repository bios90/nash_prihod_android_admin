package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.google.gson.annotations.SerializedName

class RespNoteSingle
    (
        @SerializedName("data")
        val note: ModelNote? = null
) : BaseResponse()
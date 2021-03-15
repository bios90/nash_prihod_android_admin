package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceTime
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.google.gson.annotations.SerializedName

class RespServiceText
    (
        @SerializedName("data")
        val service_text: ModelServiceText? = null
) : BaseResponse()
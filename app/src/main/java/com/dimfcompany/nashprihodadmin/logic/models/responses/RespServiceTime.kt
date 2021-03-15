package com.dimfcompany.nashprihodadmin.logic.models.responses

import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceTime
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.google.gson.annotations.SerializedName

class RespServiceTime
    (
        @SerializedName("data")
        val service_time: ModelServiceTime? = null
) : BaseResponse()
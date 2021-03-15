package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import java.io.Serializable
import java.util.*

data class ModelServiceTime(
        override var id: Long? = null,
        var time: Date?,
        var title: String?
) : Serializable, ObjectWithId
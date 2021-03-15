package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import java.io.Serializable

class ModelServiceText(
        override var id: Long? = null,
        var title: String?,
        var text: String?)
    : Serializable, ObjectWithId
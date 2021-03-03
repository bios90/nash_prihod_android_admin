package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.ObjectWithId
import com.dimfcompany.nashprihodadmin.base.enums.TypeNews
import java.io.Serializable

class ModelNews(
        override var id: Long? = null,
        var title: String? = null,
        var text: String? = null,
        var type: TypeNews? = null,
        val media_objs: ArrayList<ObjWithMedia>? = arrayListOf(),
) : Serializable, ObjectWithId
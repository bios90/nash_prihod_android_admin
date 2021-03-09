package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.enums.TypeSort
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import java.io.Serializable


class FilterDataUsers
    (
        var search_text: String? = null,
        var status: TypeUserStatus? = null,
        var sort: TypeSort = TypeSort.BY_NAME
) : Serializable
package com.dimfcompany.nashprihodadmin.logic.models

import com.dimfcompany.nashprihodadmin.base.enums.TypeNoteStatus
import com.dimfcompany.nashprihodadmin.base.enums.TypeSort
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import java.io.Serializable

class FilterDataNotes
    (
        var search_text: String? = null,
        var status: TypeNoteStatus? = null
) : Serializable


package com.dimfcompany.nashprihodadmin.ui.act_user_show

import android.view.View
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.ViewWithOverlay
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import java.util.*

interface ActUserShowMvp
{
    interface MvpView : BaseMvpView<Presenter>, ViewWithOverlay
    {
        fun bindUser(user: ModelUser)
        fun bindNotes(info: FeedDisplayInfo<ModelNote>)
    }

    interface Presenter
    {
        fun clickedEdit()
        fun clickedChangeStatus(status: TypeUserStatus)
        fun clickedNote(note: ModelNote)
        fun clickedAvatar()
    }
}
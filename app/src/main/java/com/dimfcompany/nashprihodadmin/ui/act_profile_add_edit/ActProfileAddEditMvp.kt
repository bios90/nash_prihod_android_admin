package com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser

interface ActProfileAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindUser(user: ModelUser)
    }

    interface Presenter
    {
        fun clickedSave()
    }
}
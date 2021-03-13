package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser

interface LaProfileMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindUser(user: ModelUser)
    }

    interface Presenter
    {
        fun clickedEdit()
        fun clickedLogOut()
    }
}
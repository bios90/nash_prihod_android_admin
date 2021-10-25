package com.dimfcompany.nashprihodadmin.ui.act_service_show

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText

interface ActServiceShowMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindService(service: ModelService)
    }

    interface Presenter
    {
    }
}
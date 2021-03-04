package com.dimfcompany.nashprihodadmin.ui.act_service_text_add

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActServiceTextAddMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtTextTitle(): String?
        fun getEtTextContent(): String?
    }

    interface Presenter
    {
        fun clickedAdd()
    }
}
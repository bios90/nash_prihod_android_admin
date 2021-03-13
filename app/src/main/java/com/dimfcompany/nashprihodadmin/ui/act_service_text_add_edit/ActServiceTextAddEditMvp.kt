package com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActServiceTextAddEditMvp
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
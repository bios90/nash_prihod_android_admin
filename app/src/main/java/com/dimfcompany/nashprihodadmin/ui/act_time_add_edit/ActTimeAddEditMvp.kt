package com.dimfcompany.nashprihodadmin.ui.act_time_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActTimeAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getServiceTime(): String?
        fun getServiceTitle(): String?

    }

    interface Presenter
    {
        fun clickedAdd()
        fun clickedTime()
    }
}
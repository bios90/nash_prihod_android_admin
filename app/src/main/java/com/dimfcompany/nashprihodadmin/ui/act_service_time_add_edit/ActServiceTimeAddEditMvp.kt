package com.dimfcompany.nashprihodadmin.ui.act_service_time_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import java.util.*

interface ActServiceTimeAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getServiceTime(): String?
        fun getServiceTitle(): String?
        fun bindTime(time: Date)
        fun bindText(text: String?)
    }

    interface Presenter
    {
        fun clickedAdd()
        fun clickedTime()
    }
}
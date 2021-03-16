package com.dimfcompany.nashprihodadmin.ui.act_service_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceTime
import java.util.*

interface ActServiceAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtTitleText(): String?
        fun bindServiceDate(date: Date)
        fun bindServiceText(text: String?)
        fun bindBtnSaveText(text: String?)

        fun addServiceTime(service_time: ModelServiceTime)
        fun updateServiceTime(service_time: ModelServiceTime, pos: Int)
        fun removeServiceTimeAtPos(pos: Int)

        fun addServiceText(service_text: ModelServiceText)
        fun updateServiceText(service_text: ModelServiceText, pos: Int)
        fun removeServiceTextAtPos(pos: Int)
    }

    interface Presenter
    {
        fun clickedSave()
        fun clickedDate()
        fun clickedAddServiceTime()
        fun clickedAddServiceText()
        fun clickedServiceTime(service_time: ModelServiceTime)
        fun clickedServiceText(service_text: ModelServiceText)
    }
}



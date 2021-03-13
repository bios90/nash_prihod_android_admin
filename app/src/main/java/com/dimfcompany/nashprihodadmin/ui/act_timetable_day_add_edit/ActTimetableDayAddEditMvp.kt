package com.dimfcompany.nashprihodadmin.ui.act_timetable_day_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelTimetableServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelTimetableTime

interface ActTimetableDayAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun addTimetableTime(timetableTime: ModelTimetableTime)
        fun addTimetableServiceText(timetableServiceText: ModelTimetableServiceText)
    }

    interface Presenter
    {
        fun clickedAddTime()
        fun clickedAddServiceText()
    }
}



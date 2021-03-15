package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable

import android.view.View
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_service_add_edit.ActServiceAddEdit

class TabTimeTable(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaTimeTableMvp.MvpView

    init
    {
        mvp_view = act_main.view_factory.getLaTimeTableMvpView(null)
        mvp_view.registerPresenter(PresenterImplementer())
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }

    inner class PresenterImplementer : LaTimeTableMvp.Presenter
    {
        override fun clickedAddTimetableDay()
        {
            BuilderIntent()
                    .setActivityToStart(ActServiceAddEdit::class.java)
                    .startActivity(act_main)
        }

    }
}
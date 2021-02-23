package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable

import android.view.View
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter

class TabTimeTable(act_main: ActMain) : TabPresenter
{
    val mvp_view: LaTimeTableMvp.MvpView

    init
    {
        mvp_view = act_main.view_factory.getLaTimeTableMvpView(null)
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
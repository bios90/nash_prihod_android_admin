package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes

import android.view.View
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter

class TabNotes(act_main: ActMain) : TabPresenter
{
    val mvp_view: LaNotesMvp.MvpView

    init
    {
        mvp_view = act_main.view_factory.getLaNotesMvpView(null)
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
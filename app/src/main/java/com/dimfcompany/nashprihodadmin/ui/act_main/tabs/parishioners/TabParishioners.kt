package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.parishioners

import android.view.View
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter

class TabParishioners(act_main: ActMain) : TabPresenter
{
    val mvp_view: LaParishionersMvp.MvpView

    init
    {
        mvp_view = act_main.view_factory.getLaVisitorsMvpView(null)
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
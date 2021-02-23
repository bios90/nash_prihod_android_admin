package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import android.view.View
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter

class TabNews(act_main: ActMain) : TabPresenter
{
    val mvp_view: LaNewsMvp.MvpView

    init
    {
        mvp_view = act_main.view_factory.getLaNewsMvpView(null)
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
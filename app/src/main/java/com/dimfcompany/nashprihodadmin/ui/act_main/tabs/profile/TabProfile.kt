package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile

import android.view.View
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter

class TabProfile(act_main: ActMain) : TabPresenter
{
    val mvp_view: LaProfileMvp.MvpView

    init
    {
        mvp_view = act_main.view_factory.getLaProfileMvpView(null)

    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
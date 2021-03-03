package com.dimfcompany.nashprihodadmin.ui.act_main

import android.view.View
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActMainMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun setViews(views: ArrayList<View>)
        fun scrollToTab(tab: TypeTab)
    }

    interface Presenter
    {
        fun clickedTab(tab: TypeTab)
    }
}
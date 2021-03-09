package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface LaVisitorsMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
    }

    interface Presenter
    {
        fun clickedFilter()
    }
}
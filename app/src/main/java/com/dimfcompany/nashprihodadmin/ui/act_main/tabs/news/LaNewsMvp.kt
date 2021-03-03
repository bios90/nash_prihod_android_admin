package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface LaNewsMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
    }

    interface Presenter
    {
        fun clickedAddNews()
    }
}
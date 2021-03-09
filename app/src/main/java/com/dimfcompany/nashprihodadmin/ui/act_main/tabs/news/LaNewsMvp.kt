package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.base.adapters.BaseRvListener
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser

interface LaNewsMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindItems(info: FeedDisplayInfo<ModelNews>)
    }

    interface Presenter : BaseRvListener<ModelNews>
    {
        fun clickedAddNews()
    }
}
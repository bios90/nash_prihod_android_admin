package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.base.adapters.BaseRvListener
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser

interface LaNewsMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindNewsItems(info: FeedDisplayInfo<ModelNews>)
        fun bindNoticesItems(info: FeedDisplayInfo<ModelNotice>)
    }

    interface Presenter
    {
        fun clickedAddNews()
        fun clickedAddNotice()
        fun clickedNews(news: ModelNews)
        fun clickedNotice(notice: ModelNotice)
    }
}
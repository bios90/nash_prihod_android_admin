package com.dimfcompany.nashprihodadmin.ui.act_news_show

import android.view.View
import android.view.ViewGroup
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews

interface ActNewsShowMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindNews(news: ModelNews)
        fun getViewForCarousel(): ViewGroup
    }

    interface Presenter
    {
    }
}
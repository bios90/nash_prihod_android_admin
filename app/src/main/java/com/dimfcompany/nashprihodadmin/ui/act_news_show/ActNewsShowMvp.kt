package com.dimfcompany.nashprihodadmin.ui.act_news_show

import android.view.View
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews

interface ActNewsShowMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindViews(views: ArrayList<View>)
        fun bindNews(news: ModelNews)
    }

    interface Presenter
    {
    }
}
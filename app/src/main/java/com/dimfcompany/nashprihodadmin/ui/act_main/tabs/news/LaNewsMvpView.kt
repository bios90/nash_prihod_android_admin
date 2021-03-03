package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActMainBinding
import com.dimfcompany.nashprihodadmin.databinding.LaNewsBinding

class LaNewsMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaNewsMvp.Presenter>(), LaNewsMvp.MvpView
{
    val bnd_la_news: LaNewsBinding

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_news, parent, false)
        setRootView(bnd_la_news.root)

        setListeners()
    }

    private fun setListeners()
    {
        bnd_la_news.tvAddNews.setOnClickListener(
            {
                getPresenter().clickedAddNews()
            })
    }
}
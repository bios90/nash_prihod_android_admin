package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.adapters.AdapterRvEvents
import com.dimfcompany.nashprihodadmin.base.adapters.AdapterRvNotices
import com.dimfcompany.nashprihodadmin.base.adapters.BaseRvListener
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.setDivider
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaNewsBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice

class LaNewsMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaNewsMvp.Presenter>(), LaNewsMvp.MvpView
{
    val bnd_la_news: LaNewsBinding
    val adapter_rv_events = AdapterRvEvents()
    val adapter_rv_notices = AdapterRvNotices()

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_news, parent, false)
        setRootView(bnd_la_news.root)

        initRecyclers()
        setListeners()
    }

    override fun bindNewsItems(info: FeedDisplayInfo<ModelNews>)
    {
        adapter_rv_events.setItems(info)
    }

    override fun bindNoticesItems(info: FeedDisplayInfo<ModelNotice>)
    {
        adapter_rv_notices.setItems(info)

        val need_to_show_top_view = (info.items.size > 0).toVisibility()
        bnd_la_news.recNotices.visibility = need_to_show_top_view
    }

    private fun setListeners()
    {
        bnd_la_news.tvAddNews.setOnClickListener(
            {
                getPresenter().clickedAddNews()
            })

        bnd_la_news.tvAddNotice.setOnClickListener(
            {
                getPresenter().clickedAddNotice()
            })
    }

    private fun initRecyclers()
    {
        bnd_la_news.recNews.layoutManager = LinearLayoutManager(getRootView().context)
        bnd_la_news.recNews.adapter = adapter_rv_events
        bnd_la_news.recNews.setDivider(getColorMy(R.color.transparent), dp2pxInt(8f))
        adapter_rv_events.listener = (
                {
                    getPresenter().clickedNews(it)
                })

        bnd_la_news.recNotices.layoutManager = LinearLayoutManager(getRootView().context, LinearLayoutManager.HORIZONTAL, false)
        bnd_la_news.recNotices.adapter = adapter_rv_notices
        bnd_la_news.recNotices.setDivider(getColorMy(R.color.transparent), dp2pxInt(12f), DividerItemDecoration.HORIZONTAL)
        adapter_rv_notices.listener = (
                {
                    getPresenter().clickedNotice(it)
                })
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.adapters.AdapterRvEvents
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.setDivider
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActMainBinding
import com.dimfcompany.nashprihodadmin.databinding.LaNewsBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews

class LaNewsMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaNewsMvp.Presenter>(), LaNewsMvp.MvpView
{
    val bnd_la_news: LaNewsBinding
    val adapter_rv_events = AdapterRvEvents()

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_news, parent, false)
        setRootView(bnd_la_news.root)

        initRecyclers()
        setListeners()
    }

    override fun bindItems(info: FeedDisplayInfo<ModelNews>)
    {
        adapter_rv_events.setItems(info)
    }

    override fun registerPresenter(presenter: LaNewsMvp.Presenter)
    {
        super.registerPresenter(presenter)
        adapter_rv_events.listener = presenter
    }

    private fun setListeners()
    {
        bnd_la_news.tvAddNews.setOnClickListener(
            {
                getPresenter().clickedAddNews()
            })
    }

    private fun initRecyclers()
    {
        bnd_la_news.recNews.layoutManager = LinearLayoutManager(getRootView().context)
        bnd_la_news.recNews.adapter = adapter_rv_events
        bnd_la_news.recNews.setDivider(getColorMy(R.color.transparent), dp2pxInt(8f))
    }
}
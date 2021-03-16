package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.adapters.AdapterRvServices
import com.dimfcompany.nashprihodadmin.base.extensions.applyMyStyle
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.setDivider
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.*
import com.dimfcompany.nashprihodadmin.logic.models.ModelService

class LaTimeTableMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaTimeTableMvp.Presenter>(), LaTimeTableMvp.MvpView
{
    val bnd_la_news: LaTimetableBinding
    val adapter = AdapterRvServices()

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_timetable, parent, false)
        setRootView(bnd_la_news.root)

        initRecyclers()
        setListeners()
    }

    private fun setListeners()
    {
        bnd_la_news.tvAddTimetableEvent.setOnClickListener(
            {
                getPresenter().clickedAddTimetableDay()
            })

        bnd_la_news.srlServices.setOnRefreshListener(
            {
                getPresenter().swipedToRefresh()
            })

        adapter.listener =
                {
                    getPresenter().clickedService(it)
                }
    }

    override fun bindServices(info: FeedDisplayInfo<ModelService>)
    {
        bnd_la_news.srlServices.isRefreshing = false
        adapter.setItems(info)
    }

    private fun initRecyclers()
    {
        bnd_la_news.srlServices.applyMyStyle()

        bnd_la_news.recServices.layoutManager = LinearLayoutManager(getRootView().context)
        bnd_la_news.recServices.adapter = adapter
        bnd_la_news.recServices.setDivider(getColorMy(R.color.transparent), dp2pxInt(8f))
    }
}
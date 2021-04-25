package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.adapters.AdapterRvUsers
import com.dimfcompany.nashprihodadmin.base.adapters.CustomLaManager
import com.dimfcompany.nashprihodadmin.base.extensions.applyMyStyle
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.setDivider
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaVisitorsBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser

class LaVisitorsMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaVisitorsMvp.Presenter>(), LaVisitorsMvp.MvpView
{
    val bnd_la_visitors: LaVisitorsBinding
    val adapater = AdapterRvUsers()

    init
    {
        bnd_la_visitors = DataBindingUtil.inflate(layoutInflater, R.layout.la_visitors, parent, false)
        setRootView(bnd_la_visitors.root)

        initRecyclers()
        setListeners()
    }

    private fun setListeners()
    {
        bnd_la_visitors.tvSearch.setOnClickListener(
            {
                getPresenter().clickedFilter()
            })

        bnd_la_visitors.srlUsers.setOnRefreshListener(
            {
                getPresenter().swipedToRefresh()
            })
    }

    override fun bindUsers(info: FeedDisplayInfo<ModelUser>)
    {
        bnd_la_visitors.srlUsers.isRefreshing = false
        adapater.setItems(info)
    }

    private fun initRecyclers()
    {
        bnd_la_visitors.srlUsers.applyMyStyle()

        bnd_la_visitors.recUsers.adapter = adapater
        bnd_la_visitors.recUsers.layoutManager = CustomLaManager(getRootView().context)
        bnd_la_visitors.recUsers.setDivider(getColorMy(R.color.transparent), dp2pxInt(8f))
        adapater.listener =
                {
                    getPresenter().clickedUser(it)
                }
    }
}
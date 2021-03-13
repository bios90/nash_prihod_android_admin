package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors

import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser

interface LaVisitorsMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindUsers(info: FeedDisplayInfo<ModelUser>)
    }

    interface Presenter
    {
        fun clickedFilter()
        fun clickedUser(user: ModelUser)
        fun swipedToRefresh()
    }
}
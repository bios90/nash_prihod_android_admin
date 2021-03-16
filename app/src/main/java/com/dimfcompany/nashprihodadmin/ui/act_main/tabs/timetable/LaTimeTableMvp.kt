package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable

import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelService

interface LaTimeTableMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindServices(info: FeedDisplayInfo<ModelService>)
    }

    interface Presenter
    {
        fun clickedAddTimetableDay()
        fun swipedToRefresh()
        fun clickedService(service: ModelService)
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_main

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news.TabNews
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes.TabNotes
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile.TabProfile
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable.TabTimeTable
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors.TabVisitors


class ActMain : BaseActivity()
{
    lateinit var mvp_view: ActMainMvp.MvpView

    lateinit var tab_news: TabNews
    lateinit var tab_time_table: TabTimeTable
    lateinit var tab_visitors: TabVisitors
    lateinit var tab_notes: TabNotes
    lateinit var tab_profile: TabProfile

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActMainMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setViewPager()
        setEvents()
    }

    fun setNavStatus()
    {
        is_full_screen = false
        is_below_nav_bar = false
        color_status_bar = getColorMy(R.color.white_snow)
        color_nav_bar = getColorMy(R.color.white)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun setViewPager()
    {
        tab_news = TabNews(this)
        tab_time_table = TabTimeTable(this)
        tab_visitors = TabVisitors(this)
        tab_notes = TabNotes(this)
        tab_profile = TabProfile(this)

        val views = arrayListOf(tab_news.getView(), tab_time_table.getView(), tab_visitors.getView(), tab_notes.getView(), tab_profile.getView())
        mvp_view.setViews(views)
    }

    private fun setEvents()
    {
        BusMainEvents.bs_current_tab
                .distinctUntilChanged()
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.scrollToTab(it)
                    })
                .disposeBy(composite_disposable)
    }

    inner class PresenterImplementer : ActMainMvp.Presenter
    {
        override fun clickedTab(tab: TypeTab)
        {
            BusMainEvents.bs_current_tab.onNext(tab)
        }
    }
}
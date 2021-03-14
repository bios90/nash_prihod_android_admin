package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.*
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_time_add_edit.ActTimeAddEdit

class LaTimeTableMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaTimeTableMvp.Presenter>(), LaTimeTableMvp.MvpView
{
    val bnd_la_news: LaTimetableBinding

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_timetable, parent, false)
        setRootView(bnd_la_news.root)

        setListeners()
    }

    private fun setListeners()
    {
        bnd_la_news.tvAddTimetableEvent.setOnClickListener(
            {
                getPresenter().clickedAddTimetableDay()
            })
    }
}
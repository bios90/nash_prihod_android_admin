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
    val bnd_item_date: ItemDateBinding
    val bnd_item_service_time: ItemServiceTimeBinding
    var act_time_add: ActTimeAddEdit

    init
    {
        act_time_add = ActTimeAddEdit()
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_timetable, parent, false)
        setRootView(bnd_la_news.root)
        bnd_item_date = DataBindingUtil.inflate(layoutInflater, R.layout.item_date, parent, false)
        bnd_item_service_time = DataBindingUtil.inflate(layoutInflater, R.layout.item_service_time, parent, false)

    }
}
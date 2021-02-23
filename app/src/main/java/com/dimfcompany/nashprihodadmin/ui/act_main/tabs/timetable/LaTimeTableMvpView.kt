package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActMainBinding
import com.dimfcompany.nashprihodadmin.databinding.LaNewsBinding
import com.dimfcompany.nashprihodadmin.databinding.LaTimetableBinding

class LaTimeTableMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaTimeTableMvp.Presenter>(), LaTimeTableMvp.MvpView
{
    val bnd_la_news: LaTimetableBinding

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_timetable, parent, false)
        setRootView(bnd_la_news.root)
    }
}
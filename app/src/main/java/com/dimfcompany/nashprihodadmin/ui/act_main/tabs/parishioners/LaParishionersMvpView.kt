package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.parishioners

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaParishionersBinding

class LaParishionersMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaParishionersMvp.Presenter>(), LaParishionersMvp.MvpView
{
    val bnd_la_news: LaParishionersBinding

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_parishioners, parent, false)
        setRootView(bnd_la_news.root)
    }
}
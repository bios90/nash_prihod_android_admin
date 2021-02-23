package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActMainBinding
import com.dimfcompany.nashprihodadmin.databinding.LaNewsBinding
import com.dimfcompany.nashprihodadmin.databinding.LaNotesBinding

class LaNotesMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaNotesMvp.Presenter>(), LaNotesMvp.MvpView
{
    val bnd_la_news: LaNotesBinding

    init
    {
        bnd_la_news = DataBindingUtil.inflate(layoutInflater, R.layout.la_notes, parent, false)
        setRootView(bnd_la_news.root)
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import android.view.View
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderAlerter
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegister

class TabNews(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaNewsMvp.MvpView

    init
    {
        mvp_view = act_main.view_factory.getLaNewsMvpView(null)
        mvp_view.registerPresenter(PresenterImplementer())
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }


    inner class PresenterImplementer() : LaNewsMvp.Presenter
    {
        override fun clickedAddNews()
        {
            BuilderIntent()
                    .setActivityToStart(ActNewsAddEdit::class.java)
                    .startActivity(act_main)
        }
    }
}
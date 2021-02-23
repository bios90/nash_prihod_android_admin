package com.dimfcompany.nashprihodadmin.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirstMvp
import com.dimfcompany.nashprihodadmin.ui.act_first.ActFirstMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMainMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMainMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news.LaNewsMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news.LaNewsMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes.LaNotesMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes.LaNotesMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile.LaProfileMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile.LaProfileMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable.LaTimeTableMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable.LaTimeTableMvpView
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors.LaVisitorsMvp
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors.LaVisitorsMvpView
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegisterMvp
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegisterMvpView
import javax.inject.Inject

class ViewFactory @Inject constructor(private val activity: BaseActivity)
{
    val layout_inflater: LayoutInflater

    init
    {
        layout_inflater = activity.layoutInflater
    }

    fun getActFirstMvpView(parent: ViewGroup?): ActFirstMvp.MvpView
    {
        return ActFirstMvpView(layout_inflater, parent)
    }

    fun getActRegisterMvpView(parent: ViewGroup?): ActRegisterMvp.MvpView
    {
        return ActRegisterMvpView(layout_inflater, parent)
    }

    fun getActMainMvpView(parent: ViewGroup?): ActMainMvp.MvpView
    {
        return ActMainMvpView(layout_inflater, parent)
    }

    fun getLaNewsMvpView(parent: ViewGroup?): LaNewsMvp.MvpView
    {
        return LaNewsMvpView(layout_inflater, parent)
    }

    fun getLaTimeTableMvpView(parent: ViewGroup?): LaTimeTableMvp.MvpView
    {
        return LaTimeTableMvpView(layout_inflater, parent)
    }

    fun getLaVisitorsMvpView(parent: ViewGroup?): LaVisitorsMvp.MvpView
    {
        return LaVisitorsMvpView(layout_inflater, parent)
    }

    fun getLaNotesMvpView(parent: ViewGroup?): LaNotesMvp.MvpView
    {
        return LaNotesMvpView(layout_inflater, parent)
    }

    fun getLaProfileMvpView(parent: ViewGroup?): LaProfileMvp.MvpView
    {
        return LaProfileMvpView(layout_inflater, parent)
    }
}
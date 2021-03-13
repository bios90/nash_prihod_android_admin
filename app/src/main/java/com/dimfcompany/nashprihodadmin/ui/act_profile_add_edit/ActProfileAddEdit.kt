package com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy

class ActProfileAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActProfileAddEditMvp.MvpView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActProfileAddEditMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())
    }

    fun setNavStatus()
    {
        is_full_screen = true
        is_below_nav_bar = true
        color_status_bar = getColorMy(R.color.transparent)
        color_nav_bar = getColorMy(R.color.transparent)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    inner class PresenterImplementer : ActProfileAddEditMvp.Presenter
    {
        override fun clickedSave()
        {

        }

    }
}
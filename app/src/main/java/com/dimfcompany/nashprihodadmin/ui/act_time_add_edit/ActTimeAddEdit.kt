package com.dimfcompany.nashprihodadmin.ui.act_time_add_edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEdit

class ActTimeAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActTimeAddEditMvp.MvpView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActTimeAddEditMvpView(null)
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

    inner class PresenterImplementer : ActTimeAddEditMvp.Presenter
    {
        override fun clickedAdd()
        {
            BuilderIntent()
                    .setActivityToStart(ActServiceTextAddEdit::class.java)
                    .startActivity(this@ActTimeAddEdit)
        }
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_service_text_add

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEdit

class ActServiceTextAdd : BaseActivity()
{
    lateinit var mvp_view: ActServiceTextAddMvp.MvpView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActServiceTextAddMvpView(null)
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

    inner class PresenterImplementer : ActServiceTextAddMvp.Presenter
    {
        override fun clickedAdd()
        {
            mvp_view.getEtTextTitle()
            mvp_view.getEtTextContent()
        }

    }
}
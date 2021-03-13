package com.dimfcompany.nashprihodadmin.ui.act_time_add_edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelTimetableTime
import java.util.*

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
            Log.e("ADD Time", "clickedAdd: Time", )
            val timetable_time = ModelTimetableTime(Date(), mvp_view.getServiceTitle())
            val data = ValidationManager.validateTimetableTimeAddEdit(timetable_time)
            if (!data.is_valid)
            {
                data.show(this@ActTimeAddEdit)
                return
            }
            val return_intent = Intent()
            return_intent.putExtra(Constants.Extras.MODEL_TIMETABLE_TIME, timetable_time)
            finishWithResultOk(return_intent)
        }

        override fun clickedTime()
        {

        }
    }
}
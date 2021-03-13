package com.dimfcompany.nashprihodadmin.ui.act_timetable_day_add_edit

import android.os.Bundle
import android.util.Log
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.logic.models.ModelTimetableServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelTimetableTime
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit.ActServiceTextAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_time_add_edit.ActTimeAddEdit

class ActTimetableDayAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActTimetableDayAddEditMvp.MvpView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActTimetableDayAddEditMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())
    }

    fun setNavStatus()
    {
        is_full_screen = false
        is_below_nav_bar = false
        color_status_bar = getColorMy(R.color.white_snow)
        color_nav_bar = getColorMy(R.color.white_snow)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    inner class PresenterImplementer : ActTimetableDayAddEditMvp.Presenter
    {
        override fun clickedAddTime()
        {
            Log.e("TAG", "clickedAddTime: ")
            BuilderIntent().setActivityToStart(ActTimeAddEdit::class.java)
                    .setOkAction(
                            {
                                val timetable_time = it?.getSerializableExtra(Constants.Extras.MODEL_TIMETABLE_TIME) as? ModelTimetableTime
                                if (timetable_time != null)
                                {
                                     mvp_view.addTimetableTime(timetable_time)
                                }
                            })
                    .startActivity(this@ActTimetableDayAddEdit)
        }

        override fun clickedAddServiceText()
        {
            Log.e("TAG", "clickedAddServiceText: " )
            BuilderIntent().setActivityToStart(ActServiceTextAddEdit::class.java)
                    .setOkAction (
                            {
                                val timetable_service_text = it?.getSerializableExtra(Constants.Extras
                                        .MODEL_TIMETABLE_SERVICE_TEXT) as? ModelTimetableServiceText
                                if (timetable_service_text != null)
                                {
                                    mvp_view.addTimetableServiceText(timetable_service_text)
                                }
                            })
                    .startActivity(this@ActTimetableDayAddEdit)
        }

    }
}
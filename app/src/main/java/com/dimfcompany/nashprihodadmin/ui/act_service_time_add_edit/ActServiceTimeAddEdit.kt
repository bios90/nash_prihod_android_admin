package com.dimfcompany.nashprihodadmin.ui.act_service_time_add_edit

import android.content.Intent
import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceTime
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderTimeDialog
import com.dimfcompany.nashprihodadmin.logic.utils.setHoursMy
import com.dimfcompany.nashprihodadmin.logic.utils.setMinutesMy
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class ActServiceTimeAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActServiceTimeAddEditMvp.MvpView
    val bs_time: BehaviorSubject<Date> = BehaviorSubject.create()
    val bs_service_time_to_edit: BehaviorSubject<ModelServiceTime> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActTimeAddEditMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        Date().setHoursMy(9)
                .setMinutesMy(0)
                .sendInBs(bs_time)

        checkExtra()
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

    private fun setEvents()
    {
        bs_time
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindTime(it)
                    })
                .disposeBy(composite_disposable)

        bs_service_time_to_edit
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindText(it.title)
                        it.time?.let(
                            {
                                bs_time.onNext(it)
                            })
                    })
                .disposeBy(composite_disposable)
    }

    private fun checkExtra()
    {
        val service_time = intent.getSerializableExtra(Constants.Extras.MODEL_SERVICE_TIME) as ModelServiceTime?
        if (service_time != null)
        {
            bs_service_time_to_edit.onNext(service_time)
        }
    }

    inner class PresenterImplementer : ActServiceTimeAddEditMvp.Presenter
    {
        override fun clickedAdd()
        {
            val date = bs_time.value ?: Date()
            val timetable_time = ModelServiceTime(null, date, mvp_view.getServiceTitle())

            val data = ValidationManager.validateTimetableTimeAddEdit(timetable_time)
            if (!data.is_valid)
            {
                data.show(this@ActServiceTimeAddEdit)
                return
            }

            val return_intent = Intent()
            return_intent.putExtra(Constants.Extras.MODEL_SERVICE_TIME, timetable_time)
            finishWithResultOk(return_intent)
        }

        override fun clickedTime()
        {
            BuilderTimeDialog()
                    .setDateCurrent(bs_time.value ?: Date())
                    .setTitle(getStringMy(R.string.select_time))
                    .setActionSuccess(
                        {
                            bs_time.onNext(it)
                        })
                    .show(supportFragmentManager)
        }
    }
}
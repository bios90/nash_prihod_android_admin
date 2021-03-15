package com.dimfcompany.nashprihodadmin.ui.act_service_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActTimetableDayAddEditBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemServiceTextBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemTimetableTimeBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceTime
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import java.util.*

class ActServiceAddEditMvpView(val layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActServiceAddEditMvp.Presenter>(), ActServiceAddEditMvp.MvpView
{
    val bnd_act_timetable_day_add_edit: ActTimetableDayAddEditBinding

    init
    {
        bnd_act_timetable_day_add_edit = DataBindingUtil.inflate(layoutInflater, R.layout.act_timetable_day_add_edit, parent, false)
        setRootView(bnd_act_timetable_day_add_edit.root)
        setListeners()
    }

    fun setListeners()
    {
        bnd_act_timetable_day_add_edit.btnAddServiceTime.setOnClickListener(
            {
                getPresenter().clickedAddServiceTime()
            })

        bnd_act_timetable_day_add_edit.btnAddServiceText.setOnClickListener(
            {
                getPresenter().clickedAddServiceText()
            })

        bnd_act_timetable_day_add_edit.tvDate.setOnClickListener(
            {
                getPresenter().clickedDate()
            })

        bnd_act_timetable_day_add_edit.tvSave.setOnClickListener(
            {
                getPresenter().clickedSave()
            })
    }

    override fun bindServiceDate(date: Date)
    {
        bnd_act_timetable_day_add_edit.tvDate.text = date.formatToString(DateManager.FORMAT_FOR_DISPLAY_FULL_MONTH)
    }

    override fun addServiceTime(service_time: ModelServiceTime)
    {
        val bnd_time: ItemTimetableTimeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_timetable_time, bnd_act_timetable_day_add_edit.lalForTime, false)
        bnd_time.tvTime.text = service_time.time?.formatToString(DateManager.FORMAT_FOR_TIME)
        bnd_time.tvTitle.text = service_time.title
        bnd_act_timetable_day_add_edit.lalForTime.addView(bnd_time.root)

        bnd_time.root.setOnClickListener(
            {
                getPresenter().clickedServiceTime(service_time)
            })
    }

    override fun updateServiceTime(service_time: ModelServiceTime, pos: Int)
    {
        val lal = bnd_act_timetable_day_add_edit.lalForTime.getChildAt(pos) as LinearLayout
        val tv_time = lal.getChildAt(0) as TextView
        val tv_text = lal.getChildAt(1) as TextView
        tv_time.text = service_time.time?.formatToString(DateManager.FORMAT_FOR_TIME)
        tv_text.text = service_time.title

        lal.setOnClickListener(
            {
                getPresenter().clickedServiceTime(service_time)
            })
    }

    override fun removeServiceTimeAtPos(pos: Int)
    {
        bnd_act_timetable_day_add_edit.lalForTime.removeViewAt(pos)
    }

    override fun addServiceText(service_text: ModelServiceText)
    {
        val bnd_service_text: ItemServiceTextBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_service_text, bnd_act_timetable_day_add_edit.lalForServiceText, false)
        bnd_service_text.tvTitle.text = service_text.title
        bnd_service_text.tvText.text = service_text.text
        bnd_act_timetable_day_add_edit.lalForServiceText.addView(bnd_service_text.root)

        bnd_service_text.root.setOnClickListener(
            {
                getPresenter().clickedServiceText(service_text)
            })
    }

    override fun updateServiceText(service_text: ModelServiceText, pos: Int)
    {
        val lal = bnd_act_timetable_day_add_edit.lalForServiceText.getChildAt(pos) as LinearLayout
        val tv_title = lal.getChildAt(0) as TextView
        val tv_text = lal.getChildAt(1) as TextView
        tv_title.text = service_text.title
        tv_text.text = service_text.text

        lal.setOnClickListener(
            {
                getPresenter().clickedServiceText(service_text)
            })
    }

    override fun removeServiceTextAtPos(pos: Int)
    {
        bnd_act_timetable_day_add_edit.lalForServiceText.removeViewAt(pos)
    }
}

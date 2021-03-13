package com.dimfcompany.nashprihodadmin.ui.act_timetable_day_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActTimetableDayAddEditBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemServiceTextBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemTimetableTimeBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelTimetableServiceText
import com.dimfcompany.nashprihodadmin.logic.models.ModelTimetableTime
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString


class ActTimetableDayAddEditMvpView(val layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActTimetableDayAddEditMvp.Presenter>(), ActTimetableDayAddEditMvp.MvpView
{
    val bnd_act_timetable_day_add_edit: ActTimetableDayAddEditBinding

    init
    {
        bnd_act_timetable_day_add_edit = DataBindingUtil.inflate(layoutInflater,
                R.layout.act_timetable_day_add_edit, parent, false)
        setRootView(bnd_act_timetable_day_add_edit.root)
        setListeners()
    }

    fun setListeners()
    {
        bnd_act_timetable_day_add_edit.btnAddTime.setOnClickListener(
                {
            getPresenter().clickedAddTime()
        })

        bnd_act_timetable_day_add_edit.btnAddServiceText.setOnClickListener({
            getPresenter().clickedAddServiceText()
        })
    }

    override fun addTimetableTime(timetableTime: ModelTimetableTime)
    {
        val bnd_time: ItemTimetableTimeBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_timetable_time, bnd_act_timetable_day_add_edit.lalForTime,false)
        bnd_time.tvServiceTime.text = timetableTime.time?.formatToString(DateManager.FORMAT_FOR_TIME)
        bnd_time.tvServiceTitle.text = timetableTime.title
        bnd_act_timetable_day_add_edit.lalForTime.addView(bnd_time.root)
    }

    override fun addTimetableServiceText(timetableServiceText: ModelTimetableServiceText)
    {
        val bnd_service_text: ItemServiceTextBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_service_text, bnd_act_timetable_day_add_edit.lalForServiceText,false)
        bnd_service_text.tvItemServiceTextTitle.text = timetableServiceText.title
        bnd_service_text.tvItemServiceText.text = timetableServiceText.service_text
        bnd_act_timetable_day_add_edit.lalForServiceText.addView(bnd_service_text.root)
    }
}

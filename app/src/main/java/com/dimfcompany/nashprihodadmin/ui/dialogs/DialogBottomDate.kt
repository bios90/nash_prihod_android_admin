package com.dimfcompany.nashprihodadmin.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.databinding.DialogBottomDateBinding
import com.dimfcompany.nashprihodadmin.logic.utils.*
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDateDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zyyoona7.wheel.WheelView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.*

class DialogBottomDate(val builder: BuilderDateDialog) : BottomSheetDialogFragment()
{
    lateinit var bnd_dialog: DialogBottomDateBinding
    val ps_date_changed: PublishSubject<Int> = PublishSubject.create()
    val composite_disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        bnd_dialog = DataBindingUtil.inflate(inflater, R.layout.dialog_bottom_date, container, false)

        val bg = BuilderBg()
                .setBgColor(getColorMy(R.color.white))
                .isDpMode(true)
                .setCornerRadiuses(12f, 12f, 0f, 0f)
                .get()

        bnd_dialog.root.background = bg

        setEvents()
        setupViews()
        setWheelDates()
        setListeners()

        bindDate(builder.date_current)
        bnd_dialog.tvTitle.text = builder.title

        return bnd_dialog.root
    }

    fun setupViews()
    {
        dialog?.setNavigationBarColor(getColorMy(R.color.white))
        dialog?.makeNotDraggable()
        dialog?.setCanceledOnTouchOutside(false)

        val bg_left = BuilderBg()
                .setBgColor(getColorMy(R.color.white))
                .setRippleColor(getColorMy(R.color.blue_trans_50))
                .isRipple(true)
                .isDpMode(true)
                .setCornerRadiuses(12f, 0f, 0f, 0f)
                .get()

        bnd_dialog.tvCancel.background = bg_left

        val bg_right = BuilderBg()
                .setBgColor(getColorMy(R.color.white))
                .setRippleColor(getColorMy(R.color.blue_trans_50))
                .isRipple(true)
                .isDpMode(true)
                .setCornerRadiuses(0f, 12f, 0f, 0f)
                .get()

        bnd_dialog.tvOk.background = bg_right
    }

    private fun setEvents()
    {
        ps_date_changed
                .mainThreaded()
                .subscribe(
                    {
                        resetDaysIfNeeded()

                        var date = getSelectedDate()

                        if (date.after(builder.date_max))
                        {
                            date = builder.date_max
                            Log.e("DialogBottomDate", "setEvents: Will set date max ${date.formatToString()}")
                            bindDate(date)
                        }

                        if (date.before(builder.date_min))
                        {
                            date = builder.date_min
                            bindDate(date)
                        }
                    })
                .disposeBy(composite_disposable)
    }

    private fun setListeners()
    {
        val action_finish =
                {
                    builder.action_success?.invoke(getSelectedDate())
                    dismiss()
                }

        bnd_dialog.tvOk.setOnClickListener(
            {
                action_finish()
            })

        bnd_dialog.tvCancel.setOnClickListener(
            {
                action_finish()
            })
    }

    private fun bindDate(date: Date)
    {
        val year = date.getYearMy()
        val year_index = getYearsStr().indexOf(year.toString())

        if (bnd_dialog.wheelYear.selectedItemPosition != year_index)
        {
            bnd_dialog.wheelYear.setSelectedItemPosition(year_index, true)
        }

        val month = date.getMonthMy()
        if (bnd_dialog.wheelMonth.selectedItemPosition != month)
        {
            bnd_dialog.wheelMonth.setSelectedItemPosition(month, true)
        }

        val day = date.getDayMy() - 1
        if (bnd_dialog.wheelDay.selectedItemPosition != day)
        {
            bnd_dialog.wheelDay.setSelectedItemPosition(day, true)
        }
    }

    private fun resetDaysIfNeeded()
    {
        val year = (bnd_dialog.wheelYear.selectedItemData as String).toInt()
        val month = bnd_dialog.wheelMonth.selectedItemPosition

        val days_count_current = bnd_dialog.wheelDay.data.size
        val days_count_real = getDaysCountInMonth(year, month)

        if (days_count_current != days_count_real)
        {
            val selected_pos = bnd_dialog.wheelDay.selectedItemPosition
            val data = getDaysStr(days_count_real)

            if (selected_pos > data.size)
            {
                bnd_dialog.wheelDay.setSelectedItemPosition(data.size, true)
            }

            bnd_dialog.wheelDay.data = getDaysStr(days_count_real)
        }
    }

    private fun getSelectedDate(): Date
    {
        val year = (bnd_dialog.wheelYear.selectedItemData as String).toInt()
        val month = bnd_dialog.wheelMonth.selectedItemPosition
        val day = (bnd_dialog.wheelDay.selectedItemData as String).toInt()

        val date = Calendar.getInstance().apply(
            {
                this.set(year, month, day)
            })
                .time

        return date
    }

    private fun setWheelDates()
    {
        val str_days = getDaysStr(31)
        val str_months = getMonthStr()
        val str_years = getYearsStr()

        bnd_dialog.wheelDay.data = str_days
        applyWheelStyle(bnd_dialog.wheelDay)

        bnd_dialog.wheelMonth.data = str_months
        applyWheelStyle(bnd_dialog.wheelMonth)

        bnd_dialog.wheelYear.data = str_years
        applyWheelStyle(bnd_dialog.wheelYear)
        bnd_dialog.wheelYear.isCyclic = false
    }

    private fun getYearsStr(): List<String>
    {
        val year_min = builder.date_min.getYearMy()
        val year_max = builder.date_max.getYearMy()
        val str_years = (year_min..year_max).map({ it.toString() })
        return str_years
    }

    private fun getDaysStr(days: Int): List<String>
    {
        return (1..days).map({ it.toString() })
    }

    private fun getMonthStr(): List<String>
    {
        val months = arrayListOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")
        return months
    }

    private fun applyWheelStyle(wheel: WheelView<Any>)
    {
        wheel.applyStyleMy()

        wheel.onItemSelectedListener = object : WheelView.OnItemSelectedListener<Any>
        {
            override fun onItemSelected(wheelView: WheelView<Any>?, data: Any?, position: Int)
            {
                ps_date_changed.onNext(wheel.id)
            }
        }
    }

    private fun getDaysCountInMonth(year: Int, month: Int): Int
    {
        if (month == 1)
        {
            if (isYearLeap(year))
            {
                return 29
            }
            else
            {
                return 28
            }
        }
        else
        {
            val is_even = month % 2 == 0
            if (is_even)
            {
                return 31
            }
            else
            {
                return 30
            }
        }
    }
}

fun WheelView<*>.applyStyleMy()
{
    this.isCurved = true
    this.isCyclic = true
    this.setTextSize(17f, true)
    this.isShowDivider = true
    this.dividerHeight = dp2px(1f)
    this.dividerColor = getColorMy(R.color.blue)
    this.setTypeface(getTypeFaceFromResource(R.font.rubik_reg))
    this.setSelectedItemTextColorRes(R.color.blue)
}
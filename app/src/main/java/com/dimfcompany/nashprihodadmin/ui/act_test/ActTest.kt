package com.dimfcompany.nashprihodadmin.ui.act_test

import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.TestJava
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.runActionWithDelay
import com.dimfcompany.nashprihodadmin.databinding.ActTestBinding
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDateDialog
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderTimeDialog
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.dimfcompany.nashprihodadmin.logic.utils.isYearLeap
import com.zyyoona7.wheel.WheelView
import dagger.android.support.DaggerAppCompatActivity
import java.util.*

class ActTest : AppCompatActivity()
{
    lateinit var bnd_act_test: ActTestBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        bnd_act_test = DataBindingUtil.setContentView(this, R.layout.act_test)



        runActionWithDelay(lifecycleScope, 2000,
            {
                BuilderIntent()
                        .setActivityToStart(ActTest2::class.java)
                        .addParam(Constants.Extras.NEWS_ID, 1234)
                        .addTransition(bnd_act_test.img, "test")
                        .addTransition(bnd_act_test.tv, "test2")
                        .startActivity(this)

//                val intent = Intent(this, ActTest2::class.java)
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, bnd_act_test.img, "test")
//                startActivity(intent, options.toBundle())
            })
    }

    fun showDateDialog()
    {
        BuilderDateDialog()
                .setTitle("Выберите дату службы")
                .setActionSuccess(
                    {
                        Log.e("ActTest", "showDateDialog: Selected date is ${it.formatToString()}")
                    })
                .show(supportFragmentManager)
    }

    fun showTimeDialog()
    {
        BuilderTimeDialog()
                .setTitle("Выберите время службы")
                .setActionSuccess(
                    {
                        Log.e("ActTest", "showDateDialog: Selected date is ${it.formatToString(DateManager.FORMAT_FOR_DISPLAY_WITH_TIME)}")
                    })
                .show(supportFragmentManager)
    }

    fun setNavStatus()
    {
//        is_full_screen = false
//        is_below_nav_bar = false
//        color_status_bar = getColorMy(R.color.green)
//        color_nav_bar = getColorMy(R.color.blue)
//        is_light_status_bar = false
//        is_light_nav_bar = false
    }

    private fun wheelPickerTest()
    {
        val picker = bnd_act_test.pickerDate

        picker.textViewMonth.text = "Месяц"
        picker.itemAlignYear
        picker.isCyclic = true
        picker.isCurved = true
    }

    private fun wheelPickerTest2()
    {
        val picker = bnd_act_test.pickerDate2

        val picker_month = picker.monthWv
        val lal_parent = picker_month.parent as LinearLayout
        lal_parent.layoutDirection = View.LAYOUT_DIRECTION_RTL

        picker.setTextSize(20f, true)
        picker.setCyclic(true)

        picker.dayTv.text = ""
        picker.monthTv.text = ""
        picker.yearTv.text = ""

//        picker_month.setIntegerNeedFormat()

        picker_month.setIntegerNeedFormat("%d месяц")
    }

    private fun wheelPickerTest3()
    {
        val date_start = Calendar.getInstance().also({ it.set(2000, 12, 22) })
        val date_end = Calendar.getInstance().also({ it.set(2020, 12, 31) })
        val date_current = Calendar.getInstance()

        val listener = object : OnTimeSelectListener
        {
            override fun onTimeSelect(date: Date?, v: View?)
            {

            }
        }

        val picker_view = TimePickerBuilder(this, listener)
                .setType(booleanArrayOf(true, true, true, false, false, false))
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("Title")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(getColorMy(R.color.blue_trans_50))//标题背景颜色 Night mode
                .setBgColor(getColorMy(R.color.yellow_type_event))//滚轮背景颜色 Night mode
                .setDate(date_current)// 如果不设置的话，默认是系统时间*/
                .setRangDate(date_start, date_end)//起始终止年月日设定
                .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build()


        picker_view.show()
    }

    fun wheelPickerTest4()
    {
        TestJava.showDialog(this)
    }

    val months = arrayListOf("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")

    fun wheelPickerTest5()
    {
        val data_days = (1..31).map({ it.toString() })
        val data_month = months
        val data_years = (1900..2050).map({ it.toString() })

        bnd_act_test.wheelDay.data = data_days
        bnd_act_test.wheelDay.isCurved = true
        bnd_act_test.wheelDay.isCyclic = true
        bnd_act_test.wheelMonth.data = data_month.toList()
        bnd_act_test.wheelMonth.isCurved = true
        bnd_act_test.wheelMonth.isCyclic = true
        bnd_act_test.wheelYear.data = data_years
        bnd_act_test.wheelYear.isCurved = true
        bnd_act_test.wheelYear.isCyclic = true

        val listner = object : WheelView.OnItemSelectedListener<Any>
        {
            override fun onItemSelected(wheelView: WheelView<Any>?, data: Any?, position: Int)
            {
                val year = (bnd_act_test.wheelYear.selectedItemData as String).toInt()

                val days_count_in_month = getDaysCount(position, year)
                val day = (bnd_act_test.wheelDay.selectedItemData as String).toInt()

                if (day > days_count_in_month)
                {
                    bnd_act_test.wheelDay.selectedItemPosition = days_count_in_month - 1
                }
                Log.e("ActTest", "Days in this month $days_count_in_month")
            }
        }

        bnd_act_test.wheelMonth.onItemSelectedListener = listner
    }

    private fun getDaysCount(month: Int, year: Int): Int
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
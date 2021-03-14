package com.dimfcompany.nashprihodadmin.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.setNavigationBarColor
import com.dimfcompany.nashprihodadmin.databinding.DialogBottomDateBinding
import com.dimfcompany.nashprihodadmin.databinding.DialogBottomTimeBinding
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderTimeDialog
import com.dimfcompany.nashprihodadmin.logic.utils.getHourMy
import com.dimfcompany.nashprihodadmin.logic.utils.getMinuteMy
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.*

class DialogBottomTime(val builder: BuilderTimeDialog) : BottomSheetDialogFragment()
{
    lateinit var bnd_dialog: DialogBottomTimeBinding
    val composite_disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        bnd_dialog = DataBindingUtil.inflate(inflater, R.layout.dialog_bottom_time, container, false)

        val bg = BuilderBg()
                .setBgColor(getColorMy(R.color.white))
                .isDpMode(true)
                .setCornerRadiuses(12f, 12f, 0f, 0f)
                .get()

        bnd_dialog.root.background = bg

        setupViews()
        setListeners()

        bnd_dialog.tvTitle.text = builder.title
        bnd_dialog.wheelHours.selectedItemPosition = builder.date_current.getHourMy()
        bnd_dialog.wheelMinutes.selectedItemPosition = builder.date_current.getMinuteMy()

        return bnd_dialog.root
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

    private fun getSelectedDate(): Date
    {
        val hours = bnd_dialog.wheelHours.selectedItemPosition
        val minutes = bnd_dialog.wheelMinutes.selectedItemPosition
        val date = Calendar.getInstance().apply(
            {
                this.set(Calendar.HOUR_OF_DAY, hours)
                this.set(Calendar.MINUTE, minutes)
            })
                .time

        return date
    }

    private fun setupViews()
    {
        dialog?.setNavigationBarColor(getColorMy(R.color.white))

        val hours = (0..23).map(
            {
                if (it > 9)
                {
                    return@map it.toString()
                }
                else
                {
                    return@map "0" + it
                }
            })

        bnd_dialog.wheelHours.data = hours
        bnd_dialog.wheelHours.applyStyleMy()

        val minutes = (0..59).map(
            {
                if (it > 9)
                {
                    return@map it.toString()
                }
                else
                {
                    return@map "0" + it
                }
            })
        bnd_dialog.wheelMinutes.data = minutes
        bnd_dialog.wheelMinutes.applyStyleMy()
    }
}
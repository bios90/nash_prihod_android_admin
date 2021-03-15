package com.dimfcompany.nashprihodadmin.ui.act_service_time_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActTimeAddEditBinding
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import java.util.*

class ActServiceTimeAddEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActServiceTimeAddEditMvp.Presenter>(), ActServiceTimeAddEditMvp.MvpView
{
    val bnd_act_time_add_edit: ActTimeAddEditBinding

    init
    {
        bnd_act_time_add_edit = DataBindingUtil.inflate(layoutInflater, R.layout.act_time_add_edit, parent, false)
        setRootView(bnd_act_time_add_edit.root)
        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_time_add_edit.tvSave.setOnClickListener(
            {
                getPresenter().clickedAdd()
            }
        )

        bnd_act_time_add_edit.tvTime.setOnClickListener(
            {
                getPresenter().clickedTime()

            })
    }

    override fun bindTime(time: Date)
    {
        val text = time.formatToString(DateManager.FORMAT_FOR_TIME)
        bnd_act_time_add_edit.tvTime.text = text
    }

    override fun bindText(text: String?)
    {
        bnd_act_time_add_edit.etService.setText(text)
    }

    override fun getServiceTime(): String?
    {
        return null
    }

    override fun getServiceTitle(): String?
    {
        return bnd_act_time_add_edit.etService.getNullableText()

    }
}
package com.dimfcompany.nashprihodadmin.ui.act_time_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActTimeAddEditBinding
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg

class ActTimeAddEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActTimeAddEditMvp.Presenter>(), ActTimeAddEditMvp.MvpView
{
    val bnd_act_time_add_edit: ActTimeAddEditBinding

    init
    {
        bnd_act_time_add_edit = DataBindingUtil.inflate(layoutInflater, R.layout.act_time_add_edit, parent, false)
        setRootView(bnd_act_time_add_edit.root)
        setListeners()
        setTimeTvBackground()
    }

    private fun setListeners()
    {
        bnd_act_time_add_edit.tvAddTime.setOnClickListener(
                {
                    getPresenter().clickedAdd()
                }
        )

        bnd_act_time_add_edit.tvTime.setOnClickListener(
                {
                    getPresenter().clickedTime()

        })
    }

    override fun getEtTextTime(): String?
    {
        return null
    }

    override fun getEtService(): String?
    {
        return null
    }

    fun setTimeTvBackground()
    {
        val bg = BuilderBg()
                .isDpMode(true)
                .setCorners(4f)
                .setStrokeWidth(1f)
                .setStrokeColor(getColorMy(R.color.gray4))
                .isRipple(true)
                .setRippleColor(getColorMy(R.color.blue_trans_50))
                .get()
        bnd_act_time_add_edit.tvTime.background = bg
    }


}
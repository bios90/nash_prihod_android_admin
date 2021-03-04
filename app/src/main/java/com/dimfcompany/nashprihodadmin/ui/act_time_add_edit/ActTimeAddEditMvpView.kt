package com.dimfcompany.nashprihodadmin.ui.act_time_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActTimeAddEditBinding

class ActTimeAddEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActTimeAddEditMvp.Presenter>(), ActTimeAddEditMvp.MvpView
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
        bnd_act_time_add_edit.tvAddTime.setOnClickListener(
                {
                    getPresenter().clickedAdd()
                }
        )
    }

    override fun getEtTextTime(): String?
    {
        return null
    }

    override fun getEtService(): String?
    {
        return null
    }


}
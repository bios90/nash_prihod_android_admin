package com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActServiceTextAddEditBinding
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg

class ActServiceTextAddEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActServiceTextAddEditMvp.Presenter>(), ActServiceTextAddEditMvp.MvpView
{
    val bnd_act_service_text_add: ActServiceTextAddEditBinding

    init
    {
        bnd_act_service_text_add = DataBindingUtil.inflate(layoutInflater, R.layout.act_service_text_add_edit, parent, false)
        setRootView(bnd_act_service_text_add.root)
        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_service_text_add.tvAddServiceText.setOnClickListener(
                {
                    getPresenter().clickedAdd()
                }
        )
    }

    override fun getEtTextTitle(): String?
    {
        return bnd_act_service_text_add.etTitleServiceText.text.toString().trim()
    }

    override fun getEtTextContent(): String?
    {
        return bnd_act_service_text_add.etContentServiceText.text.toString().trim()
    }

}
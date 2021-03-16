package com.dimfcompany.nashprihodadmin.ui.act_profile_add_edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActProfileAddEditBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.rucode.autopass.logic.utils.images.GlideManager


class ActProfileAddEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActProfileAddEditMvp.Presenter>(), ActProfileAddEditMvp.MvpView
{
    val bnd_act_profile_add_edit: ActProfileAddEditBinding

    init
    {
        bnd_act_profile_add_edit = DataBindingUtil.inflate(layoutInflater, R.layout.act_profile_add_edit, parent, false)
        setRootView(bnd_act_profile_add_edit.root)
    }

    override fun bindUser(user: ModelUser)
    {
    }


}
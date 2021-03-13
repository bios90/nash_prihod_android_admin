package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaProfileBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser

class LaProfileMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaProfileMvp.Presenter>(), LaProfileMvp.MvpView
{
    val bnd_la_profile: LaProfileBinding

    init
    {
        bnd_la_profile = DataBindingUtil.inflate(layoutInflater, R.layout.la_profile, parent, false)
        setRootView(bnd_la_profile.root)
//        bnd_la_profile.tvAboutMe.setText("")

    }

    override fun bindUser(user: ModelUser)
    {
        bnd_la_profile.tvName.text = user.getFullName()
        bnd_la_profile.tvEmail.text = user.email
//        bnd_la_profile.tvBirthdayDate =

    }
    fun setListeners()
    {
        bnd_la_profile.tvBtnEdit.setOnClickListener(
                {
                    getPresenter().clickedEdit()
                })
        bnd_la_profile.tvBtnLogout.setOnClickListener(
                {
                    getPresenter().clickedLogOut()
                })
    }
}
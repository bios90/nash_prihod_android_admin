package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaProfileBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.rucode.autopass.logic.utils.images.GlideManager

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
        GlideManager.loadImage(bnd_la_profile.profileAvatar.imgImg, user.avatar?.url)
        bnd_la_profile.tvName.text = user.getFullName()
        bnd_la_profile.lalAboutUser.tvEmail.text = user.email
        //TODO chaged later
        bnd_la_profile.lalAboutUser.tvBirthdayDate.text = user.created?.formatToString(DateManager.FORMAT_FOR_DISPLAY_FULL_MONTH)
        bnd_la_profile.lalAboutUser.tvNamedayDate.text = user.created?.formatToString(DateManager.FORMAT_FOR_DISPLAY_FULL_MONTH)
        bnd_la_profile.lalAboutUser.tvPhoneNumber.text = user.phone
        bnd_la_profile.lalAboutUser.tvAboutMe.text = user.about_me

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
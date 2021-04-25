package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.base.extensions.makeCallIntent
import com.dimfcompany.nashprihodadmin.base.extensions.makeEmailIntent
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ItemAboutUserBinding
import com.dimfcompany.nashprihodadmin.databinding.LaProfileBinding
import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
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

        setListeners()
    }

    override fun bindUser(user: ModelUser)
    {
        bnd_la_profile.lalAboutUser.bindUser(user)
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

fun ItemAboutUserBinding.bindUser(user: ModelUser)
{
    GlideManager.loadImage(this.cvAvatar.imgImg, user.avatar?.url,show_failed_images = false)

    this.tvName.text = user.getFullName()
    this.tvEmail.text = user.email
    this.tvStatus.text = user.status?.getNameForDisplay()

    this.tvBirthdayDate.text = user.birthday?.formatToString(DateManager.FORMAT_DATE_WITHOUT_YEAR)
    this.lalBirthday.visibility = (user.birthday != null).toVisibility()

    this.tvNamedayDate.text = user.name_day?.formatToString(DateManager.FORMAT_DATE_WITHOUT_YEAR)
    this.lalNameday.visibility = (user.name_day != null).toVisibility()

    this.tvPhoneNumber.text = user.phone
    this.lalPhone.visibility = (user.phone != null).toVisibility()

    this.tvAboutMe.text = user.about_me
    this.tvAboutMe.visibility = (user.about_me != null).toVisibility()


    if (user.id != SharedPrefsManager.pref_current_user.get().value?.id)
    {
        this.lalPhone.setOnClickListener(
            {
                val phone = user.phone ?: return@setOnClickListener
                makeCallIntent(phone)
            })

        this.lalEmail.setOnClickListener(
            {
                val email = user.email ?: return@setOnClickListener
                makeEmailIntent(email, null, getStringMy(R.string.letter_from_nash_prihod_app))
            })
    }
}
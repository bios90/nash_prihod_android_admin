package com.dimfcompany.nashprihodadmin.ui.act_user_edit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActUserEditBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.rucode.autopass.logic.utils.images.GlideManager
import java.util.*

class ActUserEditMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActUserEditMvp.Presenter>(), ActUserEditMvp.MvpView
{
    val bnd_act_user_edit: ActUserEditBinding

    init
    {
        bnd_act_user_edit = DataBindingUtil.inflate(layoutInflater, R.layout.act_user_edit, parent, false)
        setRootView(bnd_act_user_edit.root)

        setMargins()
        setListeners()
    }

    private fun setMargins()
    {
        val margin_top = dp2px(24f) + getStatusBarHeight()
        val margin_bottom = dp2px(24f) + getNavbarHeight()

        bnd_act_user_edit.larCard.setMargins(null, margin_top.toInt(), null, margin_bottom.toInt())
    }

    private fun setListeners()
    {
        bnd_act_user_edit.cvAvatar.root.setOnClickListener(
            {
                getPresenter().clickedAvatar()
            })

        bnd_act_user_edit.tvBirthday.setOnClickListener(
            {
                getPresenter().clickedBirthday()
            })

        bnd_act_user_edit.tvNameday.setOnClickListener(
            {
                getPresenter().clickedNameday()
            })

        bnd_act_user_edit.tvSave.setOnClickListener(
            {
                getPresenter().clickedSave()
            })
    }

    override fun bindUser(user: ModelUser)
    {
        bnd_act_user_edit.etName.setText(user.first_name)
        bnd_act_user_edit.etLastName.setText(user.last_name)
        bnd_act_user_edit.etPhone.setText(user.phone)
        bnd_act_user_edit.etEmail.setText(user.email)
        bnd_act_user_edit.etUserAboutMe.setText(user.about_me)
    }

    override fun bindBirthdayDate(date: Date?)
    {
        val text = date?.formatToString() ?: "-"
        bnd_act_user_edit.tvBirthday.text = text
    }

    override fun bindNameDate(date: Date?)
    {
        val text = date?.formatToString(DateManager.FORMAT_DATE_WITHOUT_YEAR) ?: "-"
        bnd_act_user_edit.tvNameday.text = text
    }

    override fun bindUserAvatar(obj: ObjWithImageUrl)
    {
        GlideManager.loadImage(bnd_act_user_edit.cvAvatar.imgImg, obj.image_url,show_failed_images = false)
    }

    override fun getEtFirstNameText(): String?
    {
        return bnd_act_user_edit.etName.getNullableText()
    }

    override fun getEtLastText(): String?
    {
        return bnd_act_user_edit.etLastName.getNullableText()
    }

    override fun getEtEmailText(): String?
    {
        return bnd_act_user_edit.etEmail.getNullableText()
    }

    override fun getEtPhoneText(): String?
    {
        return bnd_act_user_edit.etPhone.getNullableText()
    }

    override fun getEtAboutMeText(): String?
    {
        return bnd_act_user_edit.etUserAboutMe.getNullableText()
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.extensions.dp2px
import com.dimfcompany.nashprihodadmin.base.extensions.getNavbarHeight
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.extensions.setMargins
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActFirstBinding
import com.dimfcompany.nashprihodadmin.databinding.ActRegisterBinding
import com.rucode.autopass.logic.utils.images.GlideManager

class ActRegisterMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActRegisterMvp.Presenter>(), ActRegisterMvp.MvpView
{
    val bnd_act_register: ActRegisterBinding

    init
    {
        bnd_act_register = DataBindingUtil.inflate(layoutInflater, R.layout.act_register, parent, false)
        setRootView(bnd_act_register.root)

        setBottomMargin()
        setListeners()
    }

    private fun setBottomMargin()
    {
        val margin_bottom = getNavbarHeight() + dp2px(12f)
        bnd_act_register.lalBottom.setMargins(null, null, null, margin_bottom.toInt())
    }

    private fun setListeners()
    {
        bnd_act_register.cvAvatar.imgImg.setOnClickListener(
            {
                getPresenter().clickedAvatar()
            })

        bnd_act_register.tvRegister.setOnClickListener(
            {
                getPresenter().clickedRegister()
            })

        bnd_act_register.tvLogin.setOnClickListener(
            {
                getPresenter().clickedLogin()
            })
    }

    override fun bindAvatarImage(obj: ObjWithImageUrl)
    {
        val url = obj.image_url ?: return
        GlideManager.loadImage(bnd_act_register.cvAvatar.imgImg, url)
    }

    override fun getFirstNameStr(): String?
    {
        return bnd_act_register.etFirstName.getNullableText()
    }

    override fun getLastNameStr(): String?
    {
        return bnd_act_register.etLastName.getNullableText()
    }

    override fun getEmailStr(): String?
    {
        return bnd_act_register.etEmail.getNullableText()
    }

    override fun getPassword1Str(): String?
    {
        return bnd_act_register.etPassword1.getNullableText()
    }

    override fun getPassword2Str(): String?
    {
        return bnd_act_register.etPassword2.getNullableText()
    }
}
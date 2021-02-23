package com.dimfcompany.nashprihodadmin.ui.act_first

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.dp2px
import com.dimfcompany.nashprihodadmin.base.extensions.getNavbarHeight
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.extensions.setMargins
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActFirstBinding

class ActFirstMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActFirstMvp.Presenter>(), ActFirstMvp.MvpView
{
    val bnd_act_first: ActFirstBinding

    init
    {
        bnd_act_first = DataBindingUtil.inflate(layoutInflater, R.layout.act_first, parent, false)
        setRootView(bnd_act_first.root)

        setBottomMargin()
        setListeners()
    }

    private fun setBottomMargin()
    {
        val margin_bottom = getNavbarHeight() + dp2px(12f)
        bnd_act_first.tvAddress.setMargins(null, null, null, margin_bottom.toInt())
    }

    private fun setListeners()
    {
        bnd_act_first.tvEnter.setOnClickListener(
            {
                getPresenter().clickedLogin()
            })

        bnd_act_first.tvRegister.setOnClickListener(
            {
                getPresenter().clickedRegister()
            })

        bnd_act_first.tvForgotPass.setOnClickListener(
            {
                getPresenter().clickedForgotPass()
            })
    }

    override fun bindEmail(email: String?)
    {
        bnd_act_first.etEmail.setText(email)
    }

    override fun getEmailStr(): String?
    {
        return bnd_act_first.etEmail.getNullableText()
    }

    override fun getPasswordStr(): String?
    {
        return bnd_act_first.etPassword.getNullableText()
    }
}
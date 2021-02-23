package com.dimfcompany.nashprihodadmin.ui.act_first

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActFirstMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindEmail(email: String?)
        fun getEmailStr(): String?
        fun getPasswordStr(): String?
    }

    interface Presenter
    {
        fun clickedLogin()
        fun clickedRegister()
        fun clickedForgotPass()
    }
}
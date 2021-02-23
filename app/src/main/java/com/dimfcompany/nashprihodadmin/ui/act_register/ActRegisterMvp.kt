package com.dimfcompany.nashprihodadmin.ui.act_register

import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActRegisterMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindAvatarImage(obj: ObjWithImageUrl)
        fun getFirstNameStr():String?
        fun getLastNameStr():String?
        fun getEmailStr():String?
        fun getPassword1Str():String?
        fun getPassword2Str():String?
    }

    interface Presenter
    {
        fun clickedAvatar()
        fun clickedRegister()
        fun clickedLogin()
    }
}
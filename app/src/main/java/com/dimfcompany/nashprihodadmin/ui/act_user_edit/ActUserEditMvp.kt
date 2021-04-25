package com.dimfcompany.nashprihodadmin.ui.act_user_edit

import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import java.util.*

interface ActUserEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindUser(user: ModelUser)
        fun bindBirthdayDate(date: Date?)
        fun bindNameDate(date: Date?)
        fun bindUserAvatar(obj: ObjWithImageUrl)

        fun getEtFirstNameText():String?
        fun getEtLastText():String?
        fun getEtEmailText():String?
        fun getEtPhoneText():String?
        fun getEtAboutMeText():String?
    }

    interface Presenter
    {
        fun clickedAvatar()
        fun clickedBirthday()
        fun clickedNameday()
        fun clickedSave()
    }
}
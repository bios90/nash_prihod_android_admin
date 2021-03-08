package com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice

interface ActNoticeAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtTitleText(): String?
        fun getEtTextText(): String?
        fun bindNotice(notice:ModelNotice)
    }

    interface Presenter
    {
        fun clickedSave()
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActNoticeAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtText(): String?
    }

    interface Presenter
    {
        fun clickedSave()
    }
}
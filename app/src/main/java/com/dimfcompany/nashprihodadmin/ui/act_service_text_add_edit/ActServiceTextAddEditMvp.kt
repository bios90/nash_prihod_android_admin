package com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText

interface ActServiceTextAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtTextTitle(): String?
        fun getEtTextContent(): String?
        fun bindServiceText(service_text: ModelServiceText)
    }

    interface Presenter
    {
        fun clickedAdd()
    }
}
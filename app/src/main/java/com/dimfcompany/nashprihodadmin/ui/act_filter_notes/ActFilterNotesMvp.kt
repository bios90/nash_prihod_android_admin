package com.dimfcompany.nashprihodadmin.ui.act_filter_notes

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataNotes
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers

interface ActFilterNotesMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtSearchText(): String?
        fun getStatusIndex(): Int
        fun bindFilterData(filter_data: FilterDataNotes)
    }

    interface Presenter
    {
        fun clickedApply()
    }
}
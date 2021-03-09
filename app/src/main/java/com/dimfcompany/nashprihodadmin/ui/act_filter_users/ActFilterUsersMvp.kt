package com.dimfcompany.nashprihodadmin.ui.act_filter_users

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers

interface ActFilterUsersMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtSearchText(): String?
        fun getStatusIndex(): Int
        fun getSortIndex(): Int
        fun bindFilterData(filter_data: FilterDataUsers)
    }

    interface Presenter
    {
        fun clickedApply()
    }
}
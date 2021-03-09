package com.dimfcompany.nashprihodadmin.ui.act_filter_users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.enums.getPosForRb
import com.dimfcompany.nashprihodadmin.base.extensions.getCheckedPosition
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.extensions.setCheckedAtPos
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActFilterUsersBinding
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers

class ActFilterUsersMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActFilterUsersMvp.Presenter>(), ActFilterUsersMvp.MvpView
{
    val bnd_filter_users: ActFilterUsersBinding

    init
    {
        bnd_filter_users = DataBindingUtil.inflate(layoutInflater, R.layout.act_filter_users, parent, false)
        setRootView(bnd_filter_users.root)

        setListeners()
    }

    private fun setListeners()
    {
        bnd_filter_users.tvApply.setOnClickListener(
            {
                getPresenter().clickedApply()
            })
    }

    override fun getEtSearchText(): String?
    {
        return bnd_filter_users.etSearch.getNullableText()
    }

    override fun getStatusIndex(): Int
    {
        return bnd_filter_users.rgStatus.getCheckedPosition()!!
    }

    override fun getSortIndex(): Int
    {
        return bnd_filter_users.rgSort.getCheckedPosition()!!
    }

    override fun bindFilterData(filter_data: FilterDataUsers)
    {
        bnd_filter_users.etSearch.setText(filter_data.search_text)
        bnd_filter_users.rgStatus.setCheckedAtPos(filter_data.status.getPosForRb())
        bnd_filter_users.rgSort.setCheckedAtPos(filter_data.sort.getPosForRb())
    }
}
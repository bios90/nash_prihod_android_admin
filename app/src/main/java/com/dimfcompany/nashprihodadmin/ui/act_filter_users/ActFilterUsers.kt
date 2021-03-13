package com.dimfcompany.nashprihodadmin.ui.act_filter_users

import android.content.Intent
import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.enums.TypeSort
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers

class ActFilterUsers : BaseActivity()
{
    lateinit var mvp_view: ActFilterUsersMvp.MvpView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActFilterUsersMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        checkExtra()
    }

    fun setNavStatus()
    {
        is_full_screen = true
        is_below_nav_bar = true
        color_status_bar = getColorMy(R.color.transparent)
        color_nav_bar = getColorMy(R.color.transparent)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun checkExtra()
    {
        val filter_data = intent.getSerializableExtra(Constants.Extras.FILTER_DATA_USER) as? FilterDataUsers
        if (filter_data != null)
        {
            mvp_view.bindFilterData(filter_data)
        }
    }

    inner class PresenterImplementer : ActFilterUsersMvp.Presenter
    {
        override fun clickedApply()
        {
            val status = TypeUserStatus.initFromRbStatus(mvp_view.getStatusIndex())
            val sort = TypeSort.initFromRbStatus(mvp_view.getSortIndex()) ?: TypeSort.BY_NAME

            val filter_data = FilterDataUsers()
            filter_data.search_text = mvp_view.getEtSearchText()
            filter_data.sort = sort
            filter_data.status = status

            val return_intent = Intent()
            return_intent.putExtra(Constants.Extras.FILTER_DATA_USER, filter_data)
            finishWithResultOk(return_intent)
        }
    }
}
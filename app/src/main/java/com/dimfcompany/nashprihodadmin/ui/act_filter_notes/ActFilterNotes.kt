package com.dimfcompany.nashprihodadmin.ui.act_filter_notes

import android.content.Intent
import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.enums.TypeNoteStatus
import com.dimfcompany.nashprihodadmin.base.enums.TypeSort
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataNotes
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers
import com.dimfcompany.nashprihodadmin.ui.act_filter_users.ActFilterUsersMvp

class ActFilterNotes : BaseActivity()
{
    lateinit var mvp_view: ActFilterNotesMvp.MvpView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActFilterNotesMvpView(null)
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
        val filter_data = intent.getSerializableExtra(Constants.Extras.FILTER_DATA_NOTES) as? FilterDataNotes
        if (filter_data != null)
        {
            mvp_view.bindFilterData(filter_data)
        }
    }

    inner class PresenterImplementer : ActFilterNotesMvp.Presenter
    {
        override fun clickedApply()
        {
            val status = TypeNoteStatus.initFromRbStatus(mvp_view.getStatusIndex())

            val filter_data = FilterDataNotes()
            filter_data.search_text = mvp_view.getEtSearchText()
            filter_data.status = status

            val return_intent = Intent()
            return_intent.putExtra(Constants.Extras.FILTER_DATA_NOTES, filter_data)
            finishWithResultOk(return_intent)
        }
    }
}
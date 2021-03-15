package com.dimfcompany.nashprihodadmin.ui.act_service_text_add_edit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelServiceText

class ActServiceTextAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActServiceTextAddEditMvp.MvpView
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActServiceTextAddMvpView(null)
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
        val service_text = intent.getSerializableExtra(Constants.Extras.MODEL_SERVICE_TEXT) as? ModelServiceText
        if (service_text != null)
        {
            mvp_view.bindServiceText(service_text)
        }
    }

    inner class PresenterImplementer : ActServiceTextAddEditMvp.Presenter
    {
        override fun clickedAdd()
        {
            val timetable_service_text = ModelServiceText(null, mvp_view.getEtTextTitle(), mvp_view.getEtTextContent())
            val data = ValidationManager.validateServiceTextAddEdit(timetable_service_text)
            if (!data.is_valid)
            {
                data.show(this@ActServiceTextAddEdit)
                return
            }

            val return_intent = Intent()
            return_intent.putExtra(Constants.Extras.MODEL_SERVICE_TEXT, timetable_service_text)
            finishWithResultOk(return_intent)
        }
    }
}
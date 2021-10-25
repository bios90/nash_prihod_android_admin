package com.dimfcompany.nashprihodadmin.ui.act_service_show

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getLongExtraMy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.ui.act_note_show.ActNoteShowMvp
import io.reactivex.subjects.BehaviorSubject
import java.lang.RuntimeException

class ActServiceShow : BaseActivity()
{
    lateinit var mvp_view: ActServiceShowMvp.MvpView
    val bs_service_to_show: BehaviorSubject<ModelService> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActServiceTextShowMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        reloadService()
    }

    fun setNavStatus()
    {
        is_full_screen = false
        is_below_nav_bar = false
        color_status_bar = getColorMy(R.color.transparent)
        color_nav_bar = getColorMy(R.color.transparent)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun reloadService()
    {
        base_networker.getServiceById(getServiceIdExtra(),
            {
                bs_service_to_show.onNext(it)
            })
    }

    private fun setEvents()
    {
        bs_service_to_show
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindService(it)
                    })
                .disposeBy(composite_disposable)
    }

    inner class PresenterImplementer : ActServiceShowMvp.Presenter
    {

    }

    private fun getServiceIdExtra(): Long
    {
        val service_id = intent.getLongExtraMy(Constants.Extras.SERVICE_ID)
        if (service_id == null)
        {
            throw RuntimeException("*** Error no service id passed ***")
        }
        return service_id
    }

}
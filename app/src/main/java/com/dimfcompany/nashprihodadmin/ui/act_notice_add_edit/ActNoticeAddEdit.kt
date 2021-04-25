package com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit

import android.os.Bundle
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getLongExtraMy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import io.reactivex.subjects.BehaviorSubject

class ActNoticeAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActNoticeAddEditMvp.MvpView
    val bs_notice_to_edit: BehaviorSubject<ModelNotice> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActNoticeAddEditMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        checkForExtra()
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

    private fun setEvents()
    {
        bs_notice_to_edit
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindNotice(it)
                    })
                .disposeBy(composite_disposable)
    }

    inner class PresenterImplementer : ActNoticeAddEditMvp.Presenter
    {
        override fun clickedSave()
        {
            val title = mvp_view.getEtTitleText()
            val text = mvp_view.getEtTextText()

            val notice = ModelNotice(bs_notice_to_edit.value?.id, title, text)

            val data = ValidationManager.validateNoticeAddEdit(notice)
            if (!data.is_valid)
            {
                data.showErrors(this@ActNoticeAddEdit)
                return
            }

            base_networker.makeNoticeUpsert(notice,
                {
                    BusMainEvents.ps_notice_add_or_edit.onNext(it.id!!)
                    finish()
                })
        }
    }

    private fun checkForExtra()
    {
        val notice_id = intent.getLongExtraMy(Constants.Extras.NOTICE_TO_EDIT)
        if (notice_id != null)
        {
            base_networker.getNoticeById(notice_id,
                {
                    bs_notice_to_edit.onNext(it)
                })
        }
    }
}
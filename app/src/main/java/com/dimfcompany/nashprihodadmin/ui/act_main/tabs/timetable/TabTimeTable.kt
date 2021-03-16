package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.timetable

import android.util.Log
import android.view.View
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.akcsl.base.LoadBehavior
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogBottom
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_service_add_edit.ActServiceAddEdit
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class TabTimeTable(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaTimeTableMvp.MvpView

    val composite_disposable = act_main.composite_disposable
    val base_networker = act_main.base_networker
    val ps_to_reload_services: PublishSubject<Optional<Long>> = PublishSubject.create()

    init
    {
        mvp_view = act_main.view_factory.getLaTimeTableMvpView(null)
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        ps_to_reload_services.onNext(Optional(null))
    }

    private fun setEvents()
    {
        BusMainEvents.ps_service_add_or_edit
                .mainThreaded()
                .subscribe(
                    { service_id ->

                        ps_to_reload_services.onNext(service_id.asOptional())
                    })
                .disposeBy(composite_disposable)

        ps_to_reload_services
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .mainThreaded()
                .subscribe(
                    {
                        base_networker.getServices(
                            {
                                mvp_view.bindServices(FeedDisplayInfo(it, LoadBehavior.UPDATE))
                            })
                    })
                .disposeBy(composite_disposable)
    }

    inner class PresenterImplementer : LaTimeTableMvp.Presenter
    {
        override fun clickedAddTimetableDay()
        {
            BuilderIntent()
                    .setActivityToStart(ActServiceAddEdit::class.java)
                    .startActivity(act_main)
        }

        override fun swipedToRefresh()
        {
            ps_to_reload_services.onNext(Optional(null))
        }

        override fun clickedService(service: ModelService)
        {
            val service_id = service.id ?: return

            BuilderDialogBottom()
                    .setText(service.date?.formatToString())
                    .setTitle(service.title)
                    .addBtn(BtnAction(getStringMy(R.string.watching),
                        {

                        }))
                    .addBtn(BtnAction(getStringMy(R.string.editing),
                        {
                            BuilderIntent()
                                    .addParam(Constants.Extras.SERVICE_TO_EDIT, service_id)
                                    .setActivityToStart(ActServiceAddEdit::class.java)
                                    .startActivity(act_main)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.delete),
                        {

                        }))
                    .show(act_main.supportFragmentManager)
        }
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
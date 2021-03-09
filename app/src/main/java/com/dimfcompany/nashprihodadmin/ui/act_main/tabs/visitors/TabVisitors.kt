package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors

import android.view.View
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_filter_users.ActFilterUsers
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import io.reactivex.subjects.BehaviorSubject

class TabVisitors(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaVisitorsMvp.MvpView
    val bs_filter_data: BehaviorSubject<FilterDataUsers> = BehaviorSubject.createDefault(FilterDataUsers())

    val composite_disposable = act_main.composite_disposable
    val base_networker = act_main.base_networker

    init
    {
        mvp_view = act_main.view_factory.getLaVisitorsMvpView(null)
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
    }

    private fun setEvents()
    {
        bs_filter_data
                .mainThreaded()
                .subscribe(
                    {

                    })
                .disposeBy(composite_disposable)
    }

    inner class PresenterImplementer() : LaVisitorsMvp.Presenter
    {
        override fun clickedFilter()
        {
            BuilderIntent()
                    .setActivityToStart(ActFilterUsers::class.java)
                    .addParam(Constants.Extras.FILTER_DATA_USER, bs_filter_data.value!!)
                    .setOkAction(
                        {
                            val filter_data = it?.getSerializableExtra(Constants.Extras.FILTER_DATA_USER) as? FilterDataUsers
                            if (filter_data != null)
                            {
                                bs_filter_data.onNext(filter_data)
                            }
                        })
                    .startActivity(act_main)
        }
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
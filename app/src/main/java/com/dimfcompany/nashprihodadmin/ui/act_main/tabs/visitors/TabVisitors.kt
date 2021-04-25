package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.visitors

import android.util.Log
import android.view.View
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.akcsl.base.LoadBehavior
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.base.getPosOfObject
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataUsers
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_filter_users.ActFilterUsers
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_user_show.ActUserShow
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


class TabVisitors(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaVisitorsMvp.MvpView
    val bs_filter_data: BehaviorSubject<FilterDataUsers> = BehaviorSubject.createDefault(FilterDataUsers())
    val bs_users: BehaviorSubject<ArrayList<ModelUser>> = BehaviorSubject.create()

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
        BusMainEvents.ps_user_updated
                .mainThreaded()
                .subscribe(
                    {
                        val current_user = bs_users.value ?: return@subscribe
                        if (current_user.getPosOfObject(it) != null)
                        {
                            reloadUsers()
                        }
                    })
                .disposeBy(composite_disposable)

        BusMainEvents.bs_push_clicked
                .mainThreaded()
                .subscribe(
                    {
                        val user_id = it.new_user_id ?: return@subscribe
                        toUserShowActivity(user_id)
                        reloadUsers()
                    })
                .disposeBy(composite_disposable)

        bs_filter_data
                .mainThreaded()
                .subscribe(
                    {
                        reloadUsers()
                    })
                .disposeBy(composite_disposable)

        bs_users
                .mainThreaded()
                .subscribe(
                    {
                        val info = FeedDisplayInfo(it, LoadBehavior.UPDATE)
                        mvp_view.bindUsers(info)
                    })
                .disposeBy(composite_disposable)
    }

    private fun reloadUsers()
    {
        val search = bs_filter_data.value?.search_text
        val status = bs_filter_data.value?.status
        val sort = bs_filter_data.value?.sort

        base_networker.getUsers(search, status, sort,
            {
                bs_users.onNext(it)
            })
    }

    private fun toUserShowActivity(user_id: Long)
    {
        BuilderIntent()
                .setActivityToStart(ActUserShow::class.java)
                .addParam(Constants.Extras.USER_ID, user_id)
                .startActivity(act_main)
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

        override fun clickedUser(user: ModelUser)
        {
            val user_id = user.id ?: return
            toUserShowActivity(user_id)
        }

        override fun swipedToRefresh()
        {
            reloadUsers()
        }
    }

    override fun getView(): View
    {
        return mvp_view.getRootView()
    }
}
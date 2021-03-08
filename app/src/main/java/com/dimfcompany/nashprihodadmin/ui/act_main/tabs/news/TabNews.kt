package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.akcsl.base.LoadBehavior
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BusMainEvents
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.base.extensions.runActionWithDelay
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderAlerter
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogBottom
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_register.ActRegister
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class TabNews(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaNewsMvp.MvpView
    val ps_to_reload_news: PublishSubject<Any> = PublishSubject.create()

    val composite_disposable = act_main.composite_disposable
    val base_networker = act_main.base_networker

    init
    {
        mvp_view = act_main.view_factory.getLaNewsMvpView(null)
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()

        runActionWithDelay(act_main.lifecycleScope, 1000,
            {
                ps_to_reload_news.onNext(Any())
            })

    }

    private fun setEvents()
    {
        BusMainEvents.ps_news_add_or_edit
                .mainThreaded()
                .subscribe(
                    {
                        ps_to_reload_news.onNext(Any())
                    })
                .disposeBy(composite_disposable)

        ps_to_reload_news
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .mainThreaded()
                .subscribe(
                    {
                        base_networker.getNews(
                            {
                                val info = FeedDisplayInfo(it, LoadBehavior.UPDATE)
                                mvp_view.bindItems(info)
                            })
                    })
                .disposeBy(composite_disposable)
    }


    override fun getView(): View
    {
        return mvp_view.getRootView()
    }


    inner class PresenterImplementer() : LaNewsMvp.Presenter
    {
        override fun clickedAddNews()
        {
            BuilderIntent()
                    .setActivityToStart(ActNewsAddEdit::class.java)
                    .startActivity(act_main)
        }

        override fun cardClicked(item: ModelNews)
        {
            val news_id = item.id ?: return

            BuilderDialogBottom()
                    .setText(item.created?.formatToString())
                    .setTitle(item.title)
                    .addBtn(BtnAction(getStringMy(R.string.watching),
                        {
                            Log.e("PresenterImplementer", "cardClicked: Willlll startttt")
                            BuilderIntent()
                                    .addParam(Constants.Extras.NEWS_TO_EDIT, news_id)
                                    .setActivityToStart(ActNewsAddEdit::class.java)
                                    .startActivity(act_main)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.editing), {}))
                    .show(act_main.supportFragmentManager)
        }
    }
}
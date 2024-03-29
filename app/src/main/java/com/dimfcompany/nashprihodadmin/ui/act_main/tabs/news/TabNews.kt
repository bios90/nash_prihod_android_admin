package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.news

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
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogBottom
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.dimfcompany.nashprihodadmin.ui.act_main.ActMain
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.TabPresenter
import com.dimfcompany.nashprihodadmin.ui.act_news_add_edit.ActNewsAddEdit
import com.dimfcompany.nashprihodadmin.ui.act_news_show.ActNewsShow
import com.dimfcompany.nashprihodadmin.ui.act_notice_add_edit.ActNoticeAddEdit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class TabNews(val act_main: ActMain) : TabPresenter
{
    val mvp_view: LaNewsMvp.MvpView
    val ps_to_reload_news: PublishSubject<Any> = PublishSubject.create()
    val ps_to_reload_notices: PublishSubject<Any> = PublishSubject.create()

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
                ps_to_reload_notices.onNext(Any())
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

        BusMainEvents.ps_notice_add_or_edit
                .mainThreaded()
                .subscribe(
                    {
                        ps_to_reload_notices.onNext(Any())
                    })
                .disposeBy(composite_disposable)

        ps_to_reload_news
                .throttleFirst(500, TimeUnit.MILLISECONDS,AndroidSchedulers.mainThread())
                .mainThreaded()
                .subscribe(
                    {
                        base_networker.getNews(
                            {
                                val info = FeedDisplayInfo(it, LoadBehavior.UPDATE)
                                mvp_view.bindNewsItems(info)
                            })
                    })
                .disposeBy(composite_disposable)

        ps_to_reload_notices
                .throttleFirst(500, TimeUnit.MILLISECONDS,AndroidSchedulers.mainThread())
                .mainThreaded()
                .subscribe(
                    {
                        base_networker.getNotices(
                            {
                                val info = FeedDisplayInfo(it, LoadBehavior.UPDATE)
                                mvp_view.bindNoticesItems(info)
                            })
                    })
                .disposeBy(composite_disposable)
    }


    override fun getView(): View
    {
        return mvp_view.getRootView()
    }

    private fun showNotice(notice_id: Long)
    {
        base_networker.getNoticeById(notice_id,
            {
                BuilderDialogMy()
                        .setViewId(R.layout.la_dialog_scrollable_tv)
                        .setTitle(it.title ?: "")
                        .setText(it.text ?: "")
                        .setBtnOk(BtnAction(getStringMy(R.string.close), {}))
                        .build(act_main)
            },
            {
                ps_to_reload_notices.onNext(Any())
            })
    }

    private fun clickedDeleteNotice(notice_id: Long)
    {
        BuilderDialogMy()
                .setViewId(R.layout.la_dialog_simple)
                .setTitle(getStringMy(R.string.deleting))
                .setText(getStringMy(R.string.delete_this_notice))
                .setBtnOk(BtnAction(getStringMy(R.string.delete),
                    {
                        base_networker.deleteNotice(notice_id,
                            {
                                ps_to_reload_notices.onNext(Any())
                            },
                            {
                                ps_to_reload_notices.onNext(Any())
                            })
                    }))
                .setBtnCancel(BtnAction.getDefaultCancel())
                .build(act_main)
    }

    private fun clickedDeleteNews(news_id:Long)
    {
        BuilderDialogMy()
                .setViewId(R.layout.la_dialog_simple)
                .setTitle(getStringMy(R.string.deleting))
                .setText(getStringMy(R.string.delete_this_news))
                .setBtnOk(BtnAction(getStringMy(R.string.delete),
                    {
                        base_networker.deleteNews(news_id,
                            {
                                ps_to_reload_news.onNext(Any())
                            },
                            {
                                ps_to_reload_news.onNext(Any())
                            })
                    }))
                .setBtnCancel(BtnAction.getDefaultCancel())
                .build(act_main)
    }


    inner class PresenterImplementer() : LaNewsMvp.Presenter
    {
        override fun clickedAddNews()
        {
            BuilderIntent()
                    .setActivityToStart(ActNewsAddEdit::class.java)
                    .startActivity(act_main)
        }

        override fun clickedAddNotice()
        {
            BuilderIntent()
                    .setActivityToStart(ActNoticeAddEdit::class.java)
                    .startActivity(act_main)
        }

        override fun clickedNews(news: ModelNews)
        {
            val news_id = news.id ?: return

            BuilderDialogBottom()
                    .setText(news.created?.formatToString())
                    .setTitle(news.title)
                    .addBtn(BtnAction(getStringMy(R.string.watching),
                        {
                            BuilderIntent()
                                    .setActivityToStart(ActNewsShow::class.java)
                                    .addParam(Constants.Extras.NEWS_ID, news_id)
                                    .startActivity(act_main)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.editing),
                        {
                            BuilderIntent()
                                    .addParam(Constants.Extras.NEWS_ID, news_id)
                                    .setActivityToStart(ActNewsAddEdit::class.java)
                                    .startActivity(act_main)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.delete),
                        {
                            clickedDeleteNews(news_id)
                        }))
                    .show(act_main.supportFragmentManager)
        }

        override fun clickedNotice(notice: ModelNotice)
        {
            val notice_id = notice.id ?: return

            BuilderDialogBottom()
                    .setText(notice.created?.formatToString())
                    .setTitle(notice.title)
                    .addBtn(BtnAction(getStringMy(R.string.watching),
                        {
                            showNotice(notice_id)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.editing),
                        {
                            BuilderIntent()
                                    .addParam(Constants.Extras.NOTICE_TO_EDIT, notice_id)
                                    .setActivityToStart(ActNoticeAddEdit::class.java)
                                    .startActivity(act_main)
                        }))
                    .addBtn(BtnAction(getStringMy(R.string.delete),
                        {
                            clickedDeleteNotice(notice_id)
                        }))
                    .show(act_main.supportFragmentManager)
        }
    }
}
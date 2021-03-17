package com.dimfcompany.nashprihodadmin.ui.act_news_add_edit

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.*
import com.dimfcompany.nashprihodadmin.base.enums.TypeNews
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.logic.ValidationManager
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.responses.RespNewsSingle
import com.dimfcompany.nashprihodadmin.logic.utils.BtnAction
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderDialogMy
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderNet
import com.dimfcompany.nashprihodadmin.networking.BaseNetworker
import com.dimfcompany.nashprihodadmin.networking.apis.makeInsertOrUpdateNews
import com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen.ActCarouselFullScreen
import io.reactivex.subjects.BehaviorSubject

class ActNewsAddEdit : BaseActivity()
{
    lateinit var mvp_view: ActNewsAddEditMvp.MvpView
    val medias: ArrayList<ObjWithMedia> = arrayListOf()
    val bs_news_to_edit: BehaviorSubject<ModelNews> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActNewsMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        checkForExtra()
    }

    fun setNavStatus()
    {
        is_full_screen = false
        is_below_nav_bar = false
        color_status_bar = getColorMy(R.color.white)
        color_nav_bar = getColorMy(R.color.white)
        is_light_status_bar = false
        is_light_nav_bar = false
    }

    private fun setEvents()
    {
        bs_news_to_edit
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindNewsToEdit(it)
                        this.medias.clear()
                        this.medias.addAll(it.media_objs ?: arrayListOf())
                    })
                .disposeBy(composite_disposable)
    }

    private fun addMediaObj(obj: ObjWithMedia)
    {
        this.medias.add(obj)
        mvp_view.addMediaObj(obj)
        runActionWithDelay(lifecycleScope, 300,
            {
                mvp_view.scrollImagesToEnd()
            })
    }

    inner class PresenterImplementer : ActNewsAddEditMvp.Presenter
    {
        override fun clickedRemoveMediaObject(obj: ObjWithMedia)
        {
            BuilderDialogMy()
                    .setViewId(R.layout.la_dialog_simple)
                    .setTitle(getStringMy(R.string.deleting))
                    .setText(getStringMy(R.string.delete_this_media_file))
                    .setBtnOk(BtnAction(getStringMy(R.string.delete),
                        {
                            val pos = medias.indexOf(obj)
                            if (medias.remove(obj))
                            {
                                mvp_view.removeMediaAtPos(pos)
                            }
                        }))
                    .setBtnCancel(BtnAction.getDefaultCancel())
                    .build(this@ActNewsAddEdit)
        }

        override fun clickedMedia()
        {
            checkAndPickImageAndVideo(
                {
                    addMediaObj(it)
                },
                {
                    addMediaObj(it)
                })
        }

        override fun clickedMediaObj(obj: ObjWithMedia)
        {
            val objs_wrapper = MediaItemsWrapper(medias)
            val start_pos = carousel_helper.bs_current_media_tab_pos.value ?: 0
            BuilderIntent()
                    .setActivityToStart(ActCarouselFullScreen::class.java)
                    .addParam(Constants.Extras.MEDIA_OBJECTS, objs_wrapper)
                    .addParam(Constants.Extras.MEDIA_OBJECTS_START_POS, start_pos)
                    .startActivity(this@ActNewsAddEdit)
        }

        override fun clickedSave()
        {
            val title = mvp_view.getEtTitleText()
            val text = mvp_view.getEtTextText()
            val type = TypeNews.initFromPos(mvp_view.getTypePos() ?: 0)

            val news = ModelNews(getIdToEditExtra(), title, text, type, medias)

            val data = ValidationManager.validateNewsAddEdit(news)
            if (!data.is_valid)
            {
                data.show(this@ActNewsAddEdit)
                return
            }

            upload(news,
                {
                    BusMainEvents.ps_news_add_or_edit.onNext(it.id!!)
                    finish()
                })
        }
    }

    private fun upload(news: ModelNews, action_result: (ModelNews) -> Unit)
    {
        BuilderNet<Any>()
                .setBaseActivity(this)
                .setActionMultiRequests(
                    {
                        val objs_media = news.media_objs ?: arrayListOf()
                        val media_ids = BaseNetworker.uploadObjsWithMedia(objs_media, api_files)

                        val news_from_server = api_news.makeInsertOrUpdateNews(news, media_ids)
                                .toObjOrThrow(RespNewsSingle::class.java)
                                .addParseCheckerForObj({ it.news?.id != null })
                                .news!!

                        action_result(news_from_server)
                    })
                .run()
    }

    private fun getIdToEditExtra(): Long?
    {
        return intent.getLongExtraMy(Constants.Extras.NEWS_ID)
    }

    private fun checkForExtra()
    {
        val news_id = getIdToEditExtra()
        if (news_id != null)
        {
            base_networker.getNewsById(news_id,
                {
                    bs_news_to_edit.onNext(it)
                })
        }
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_news_show

import android.os.Bundle
import android.util.Log
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.ObjWithImageUrl
import com.dimfcompany.nashprihodadmin.base.ObjWithVideo
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import io.reactivex.subjects.BehaviorSubject

class ActNewsShow : BaseActivity()
{
    lateinit var mvp_view: ActNewsShowMvp.MvpView
    val bs_news: BehaviorSubject<ModelNews> = BehaviorSubject.create()
    val mvp_views_of_media_objs: ArrayList<BaseMvpView<*>> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActNewsShowMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        loadNews()
        setEvents()
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
        bs_news
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view.bindNews(it)
                        bindMediaViews()
                    })
                .disposeBy(composite_disposable)
    }

    private fun bindMediaViews()
    {
        val medias = bs_news.value?.media_objs ?: return
        for (obj in medias)
        {
            if (obj.type == TypeMedia.VIDEO)
            {
                val mvp_video = view_factory.getMediaVideoMvpView(null)
                mvp_video.bindVideo((obj as ObjWithVideo).video_url!!)
                mvp_views_of_media_objs.add(mvp_video)
            }
            else if (obj.type == TypeMedia.IMAGE)
            {
                val mvp_image = view_factory.getMediaImageMvpView(null)
                mvp_image.toggleMode(false)
                mvp_image.bindImage((obj as ObjWithImageUrl).image_url)
                mvp_views_of_media_objs.add(mvp_image)
            }
        }

        val views = mvp_views_of_media_objs.map(
            {
                it.getRootView()
            })
                .toCollection(ArrayList())

        mvp_view.bindViews(views)
    }

    private fun loadNews()
    {
        val news_id = getIdToEditExtra() ?: return

        base_networker.getNewsById(news_id,
            {
                bs_news.onNext(it)
            })
    }

    inner class PresenterImplementer : ActNewsShowMvp.Presenter
    {

    }

    private fun getIdToEditExtra(): Long?
    {
        return intent.getLongExtraMy(Constants.Extras.NEWS_ID)
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_news_show

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActNewsShowMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        loadNews()
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

        carousel_helper.bs_show_full_screen_btn.onNext(true)
        carousel_helper.bs_image_zoom_enabled.onNext(false)
        carousel_helper.bs_video_bottom_padding_enabled.onNext(false)
        carousel_helper.bs_carousel_bg_color.onNext(getColorMy(R.color.white))

        carousel_helper.initToView(mvp_view.getViewForCarousel(), medias)
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
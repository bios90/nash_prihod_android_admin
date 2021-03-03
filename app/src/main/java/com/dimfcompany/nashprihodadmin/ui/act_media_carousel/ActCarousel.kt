package com.dimfcompany.nashprihodadmin.ui.act_media_carousel

import android.os.Bundle
import android.util.Log
import android.view.View
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.*
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.disposeBy
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.mainThreaded
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.ui.la_media_video.LaMediaVideoMvp
import com.dimfcompany.nashprihodadmin.ui.la_media_video.LaMediaVideoMvpView
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class ActCarousel : BaseActivity()
{
    lateinit var mvp_view: ActCarouselMvp.MvpView
    val bs_media_items: BehaviorSubject<ArrayList<ObjWithMedia>> = BehaviorSubject.create()
    val mvp_views_of_media_objs: ArrayList<BaseMvpView<*>> = arrayListOf()
    val bs_current_media_tab_pos: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)

    val ps_to_show_hide_pos_text: PublishSubject<Any> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        mvp_view = view_factory.getActCarouselMvpView(null)
        setContentView(mvp_view.getRootView())
        mvp_view.registerPresenter(PresenterImplementer())

        setEvents()
        checkExtra()
    }

    fun setNavStatus()
    {
        is_full_screen = true
        is_below_nav_bar = true
        color_status_bar = getColorMy(R.color.transparent)
        color_nav_bar = getColorMy(R.color.transparent)
        is_light_status_bar = true
        is_light_nav_bar = true
    }

    private fun setEvents()
    {
        mvp_views_of_media_objs.clear()
        mvp_view.bindViews(arrayListOf())

        bs_current_media_tab_pos
                .mainThreaded()
                .subscribe(
                    {
                        mvp_views_of_media_objs.forEachIndexed(
                            { index, media_mvp_view ->

                                if (media_mvp_view is LaMediaVideoMvpView)
                                {
                                    media_mvp_view.togglePlayPause(false)
                                }
                            })

                        val pos_text = "${it + 1} из ${mvp_views_of_media_objs.size}"
                        mvp_view.setPosText(pos_text)

                        ps_to_show_hide_pos_text.onNext(Any())
                    })
                .disposeBy(composite_disposable)

        bs_media_items
                .mainThreaded()
                .subscribe(
                    {
                        for (obj in it)
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

                        val start_pos = getExtraStartPos()
                        bs_current_media_tab_pos.onNext(start_pos)
                        mvp_view.scrollToPos(start_pos)
                    })
                .disposeBy(composite_disposable)

        ps_to_show_hide_pos_text
                .mainThreaded()
                .doOnNext(
                    {
                        Log.e("ActCarousel", "setEvents: Shoowww")
                        mvp_view.togglePosTextVisibility(true)
                    })
                .debounce(3000, TimeUnit.MILLISECONDS)
                .mainThreaded()
                .doOnNext(
                    {
                        Log.e("ActCarousel", "setEvents: Hideeee")
                        mvp_view.togglePosTextVisibility(false)
                    })
                .subscribe(
                    {

                    })
                .disposeBy(composite_disposable)
    }

    private fun checkExtra()
    {
        val items = intent.getSerializableExtra(Constants.Extras.MEDIA_OBJECTS) as? MediaItemsWrapper
        if (items == null)
        {
            throw RuntimeException("*** Error no medias send ***")
        }
        bs_media_items.onNext(items.items)
    }

    private fun getExtraStartPos(): Int
    {
        val start_pos = intent.getIntExtra(Constants.Extras.MEDIA_OBJECTS_START_POS, 0)
        return start_pos
    }

    inner class PresenterImplementer : ActCarouselMvp.Presenter
    {
        override fun tabChangedTo(pos: Int)
        {
            bs_current_media_tab_pos.onNext(pos)
        }
    }
}
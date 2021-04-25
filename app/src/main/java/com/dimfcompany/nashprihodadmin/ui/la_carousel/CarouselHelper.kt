package com.dimfcompany.nashprihodadmin.ui.la_carousel

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.*
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderIntent
import com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen.ActCarouselFullScreen
import com.dimfcompany.nashprihodadmin.ui.la_media_image.LaMediaImageMvp
import com.dimfcompany.nashprihodadmin.ui.la_media_image.LaMediaImageMvpView
import com.dimfcompany.nashprihodadmin.ui.la_media_video.LaMediaVideoMvp
import com.dimfcompany.nashprihodadmin.ui.la_media_video.LaMediaVideoMvpView
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.GlobalScope
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CarouselHelper @Inject constructor(val base_activity: BaseActivity)
{
    var parent: ViewGroup? = null
    var mvp_view_carousel: LaCarouselMvp.MvpView? = null
    val bs_current_media_tab_pos: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)
    val ps_to_show_hide_pos_text: PublishSubject<Any> = PublishSubject.create()
    val bs_image_zoom_enabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)
    val bs_video_bottom_padding_enabled: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)
    val bs_carousel_bg_color: BehaviorSubject<Int> = BehaviorSubject.createDefault(getColorMy(R.color.black))
    val bs_show_full_screen_btn: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    var items: ArrayList<ObjWithMedia> = arrayListOf()

    val mvp_views_of_media_objs: ArrayList<BaseMvpView<*>> = arrayListOf()
    var start_pos: Int = 0

    fun initToView(parent: ViewGroup, objs: ArrayList<ObjWithMedia> = arrayListOf(), start_pos: Int = 0)
    {
        base_activity.lifecycle.addObserver(LcObserver())
        setEvents()

        this.start_pos = start_pos
        this.parent = parent

        mvp_view_carousel = base_activity.view_factory.getActCarouselMvpView(parent)
        mvp_view_carousel!!.toggleBackgroundColor(bs_carousel_bg_color.value!!)
        parent.addView(mvp_view_carousel!!.getRootView())
        mvp_view_carousel!!.registerPresenter(PresenterImplementer())
        bindObjects(objs)
        mvp_view_carousel!!.scrollToPos(start_pos)
        mvp_view_carousel!!.toggleFullscreenBtn(bs_show_full_screen_btn.value!!)
    }

    fun bindObjects(items: ArrayList<ObjWithMedia>)
    {
        this.items = items
        for (obj in items)
        {
            if (obj.type == TypeMedia.VIDEO)
            {
                val mvp_video = base_activity.view_factory.getMediaVideoMvpView(null)
                mvp_video.bindVideo((obj as ObjWithVideo).video_url!!)
                mvp_video.toggleBottomNavbarPadding(bs_video_bottom_padding_enabled.value!!)
                mvp_views_of_media_objs.add(mvp_video)
            }
            else if (obj.type == TypeMedia.IMAGE)
            {
                val mvp_image = base_activity.view_factory.getMediaImageMvpView(null)
                mvp_image.toggleModeZoomMode(bs_image_zoom_enabled.value!!)
                mvp_image.bindImage((obj as ObjWithImageUrl).image_url)
                mvp_views_of_media_objs.add(mvp_image)
            }
        }

        val views = mvp_views_of_media_objs.map(
            {
                it.getRootView()
            })
                .toCollection(ArrayList())

        mvp_view_carousel?.bindViews(views)

        bs_current_media_tab_pos.onNext(start_pos)
        mvp_view_carousel?.scrollToPos(start_pos)
    }

    private fun setEvents()
    {
        bs_show_full_screen_btn
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view_carousel!!.toggleFullscreenBtn(it)
                    })
                .disposeBy(base_activity.composite_disposable)

        bs_carousel_bg_color
                .mainThreaded()
                .subscribe(
                    {
                        mvp_view_carousel?.toggleBackgroundColor(it)
                    })
                .disposeBy(base_activity.composite_disposable)

        bs_video_bottom_padding_enabled
                .mainThreaded()
                .subscribe(
                    {
                        for (mvp_view in items)
                        {
                            if (mvp_view is LaMediaVideoMvp.MvpView)
                            {
                                mvp_view.toggleBottomNavbarPadding(it)
                            }
                        }
                    })
                .disposeBy(base_activity.composite_disposable)

        bs_image_zoom_enabled
                .mainThreaded()
                .subscribe(
                    {
                        for (mvp_view in items)
                        {
                            if (mvp_view is LaMediaImageMvp.MvpView)
                            {
                                mvp_view.toggleModeZoomMode(it)
                            }
                        }
                    })
                .disposeBy(base_activity.composite_disposable)

        ps_to_show_hide_pos_text
                .mainThreaded()
                .doOnNext(
                    {
                        mvp_view_carousel?.togglePosTextVisibility(true)
                    })
                .debounce(3000, TimeUnit.MILLISECONDS)
                .mainThreaded()
                .doOnNext(
                    {
                        mvp_view_carousel?.togglePosTextVisibility(false)
                    })
                .subscribe(
                    {

                    })
                .disposeBy(base_activity.composite_disposable)

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
                        mvp_view_carousel?.setPosText(pos_text)

                        ps_to_show_hide_pos_text.onNext(Any())
                    })
                .disposeBy(base_activity.composite_disposable)
    }

    fun seekVideoToPos(pos: Int, time: Long)
    {
        runActionWithDelay(base_activity.lifecycleScope, 500,
            {
                val mvp_video = mvp_views_of_media_objs.getOrNull(pos) as? LaMediaVideoMvp.MvpView ?: return@runActionWithDelay

                mvp_video.seekToTime(time)
                mvp_video.togglePlayPause(true)
            })
    }

    inner class PresenterImplementer : LaCarouselMvp.Presenter
    {
        override fun tabChangedTo(pos: Int)
        {
            bs_current_media_tab_pos.onNext(pos)
        }

        override fun clickedFullScreen()
        {
            val objs_wrapper = MediaItemsWrapper(items)
            val start_pos = bs_current_media_tab_pos.value ?: 0

            var video_pos: Long? = null
            val mvp_view = mvp_views_of_media_objs.get(start_pos)
            var view_for_transition: View = mvp_view.getRootView()
            if (mvp_view is LaMediaVideoMvp.MvpView)
            {
                video_pos = mvp_view.getPlayTime()
                view_for_transition = (mvp_view as LaMediaVideoMvpView).bnd_media_video.playerView
            }
            else if (mvp_view is LaMediaImageMvpView)
            {
                view_for_transition = mvp_view.bnd_media_image.imgSimple
            }

            BuilderIntent()
                    .setActivityToStart(ActCarouselFullScreen::class.java)
                    .addParam(Constants.Extras.MEDIA_OBJECTS, objs_wrapper)
                    .addParam(Constants.Extras.MEDIA_OBJECTS_START_POS, start_pos)
                    .addParam(Constants.Extras.MEDIA_VIDEO_TIME, video_pos)
                    .addTransition(view_for_transition, Constants.TransitionNames.IMAGE_TO_CAROUSEL_TRANSITION)
                    .setOkAction(
                        {
                            val time_to_seek = it?.getLongExtraMy(Constants.Extras.MEDIA_VIDEO_TIME)
                            if (time_to_seek != null && mvp_view is LaMediaVideoMvp.MvpView)
                            {
                                mvp_view.seekToTime(time_to_seek)
                            }
                        })
                    .startActivity(base_activity)
        }
    }

    inner class LcObserver : LifecycleObserver
    {
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun pause()
        {
            mvp_views_of_media_objs.filter(
                {
                    it is LaMediaVideoMvp.MvpView
                })
                    .map(
                        {
                            it as LaMediaVideoMvp.MvpView
                        })
                    .forEach(
                        {
                            it.togglePlayPause(false)
                        })
        }
    }
}
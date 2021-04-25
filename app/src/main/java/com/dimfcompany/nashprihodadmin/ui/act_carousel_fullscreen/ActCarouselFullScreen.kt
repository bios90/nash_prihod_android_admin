package com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.*
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getLongExtraMy
import com.dimfcompany.nashprihodadmin.base.extensions.runActionWithDelay
import com.dimfcompany.nashprihodadmin.ui.la_media_image.LaMediaImageMvp
import com.dimfcompany.nashprihodadmin.ui.la_media_video.LaMediaVideoMvp
import java.lang.RuntimeException

class ActCarouselFullScreen : BaseActivity()
{
    lateinit var mvp_view: ActCarouselFullscreenMvp.MvpView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        makeOrintationChangable()
        postponeEnterTransition()
        mvp_view = view_factory.getActCarouselFullscreenMvpView(null)
        setContentView(mvp_view.getRootView())

        initCarousel()
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

    private fun initCarousel()
    {
        val parent = mvp_view.getRootView() as RelativeLayout
        val start_pos = getExtraStartPos()
        val objs = getExtraMediaObjects()

        carousel_helper.initToView(parent, objs, start_pos)

        val mvp_to_transition = carousel_helper.mvp_views_of_media_objs.get(start_pos) as ViewWithTransition
        mvp_to_transition.ready_for_transition_action =
                {
                    it.transitionName = Constants.TransitionNames.IMAGE_TO_CAROUSEL_TRANSITION
                    startPostponedEnterTransition()
                    mvp_to_transition.ready_for_transition_action = null
                }

        val time_to_seek = intent.getLongExtraMy(Constants.Extras.MEDIA_VIDEO_TIME)
        if (objs.get(start_pos).type == TypeMedia.VIDEO && time_to_seek != null)
        {
            carousel_helper.seekVideoToPos(start_pos, time_to_seek)
        }

    }

    private fun getExtraMediaObjects(): ArrayList<ObjWithMedia>
    {
        val wrapper = intent.getSerializableExtra(Constants.Extras.MEDIA_OBJECTS) as? MediaItemsWrapper
        if (wrapper == null)
        {
            throw RuntimeException("*** Error no medias send ***")
        }
        return wrapper.items
    }

    private fun getExtraStartPos(): Int
    {
        val start_pos = intent.getIntExtra(Constants.Extras.MEDIA_OBJECTS_START_POS, 0)
        return start_pos
    }

    override fun onBackPressed()
    {
        val pos = carousel_helper.mvp_view_carousel?.getCurrentPos()
        if (pos == null)
        {
            super.onBackPressed()
            return
        }

        val mvp_video = carousel_helper.mvp_views_of_media_objs.getOrNull(pos) as? LaMediaVideoMvp.MvpView
        if (mvp_video == null)
        {
            super.onBackPressed()
            return
        }

        val time = mvp_video.getPlayTime()
        val return_intent = Intent()
        return_intent.putExtra(Constants.Extras.MEDIA_VIDEO_TIME, time)
        finishWithResultOk(return_intent)
    }

}
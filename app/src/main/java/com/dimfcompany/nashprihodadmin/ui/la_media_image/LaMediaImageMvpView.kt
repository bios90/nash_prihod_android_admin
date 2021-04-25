package com.dimfcompany.nashprihodadmin.ui.la_media_image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaMediaImageBinding
import com.dimfcompany.nashprihodadmin.databinding.LaMediaVideoBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.rucode.autopass.logic.utils.images.GlideManager

class LaMediaImageMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaMediaImageMvp.Presenter>(), LaMediaImageMvp.MvpView
{
    val bnd_media_image: LaMediaImageBinding
    override var ready_for_transition_action: ((View) -> Unit)? = null

    init
    {
        bnd_media_image = DataBindingUtil.inflate(layoutInflater, R.layout.la_media_image, parent, false)
        setRootView(bnd_media_image.root)
    }

    override fun bindImage(uri: String?)
    {
        if (bnd_media_image.imgTouch.visibility == View.VISIBLE)
        {
            GlideManager.loadImage(bnd_media_image.imgTouch, uri, action_success =
            {
                ready_for_transition_action?.invoke(bnd_media_image.imgTouch)
            }, action_error =
            {
                ready_for_transition_action?.invoke(bnd_media_image.imgTouch)
            })
        }
        else if (bnd_media_image.imgSimple.visibility == View.VISIBLE)
        {
            GlideManager.loadImage(bnd_media_image.imgSimple, uri, action_success =
            {
                ready_for_transition_action?.invoke(bnd_media_image.imgSimple)
            }, action_error =
            {
                ready_for_transition_action?.invoke(bnd_media_image.imgSimple)
            })
        }
    }

    override fun toggleModeZoomMode(is_touchable: Boolean)
    {
        bnd_media_image.imgTouch.visibility = is_touchable.toVisibility()
        bnd_media_image.imgSimple.visibility = (!is_touchable).toVisibility()
    }

    override fun getViewForTransition(): View
    {
        if (bnd_media_image.imgTouch.visibility == View.VISIBLE)
        {
            return bnd_media_image.imgTouch
        }
        else
        {
            return bnd_media_image.imgSimple
        }
    }
}
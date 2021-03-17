package com.dimfcompany.nashprihodadmin.ui.la_media_image

import android.view.LayoutInflater
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

    init
    {
        bnd_media_image = DataBindingUtil.inflate(layoutInflater, R.layout.la_media_image, parent, false)
        setRootView(bnd_media_image.root)
    }

    override fun bindImage(uri: String?)
    {
        GlideManager.loadImage(bnd_media_image.imgTouch, uri)
        GlideManager.loadImage(bnd_media_image.imgSimple, uri)
    }

    override fun toggleMode(is_touchable: Boolean)
    {
        bnd_media_image.imgTouch.visibility = is_touchable.toVisibility()
        bnd_media_image.imgSimple.visibility = (!is_touchable).toVisibility()
    }
}
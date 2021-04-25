package com.dimfcompany.nashprihodadmin.ui.act_carousel_fullscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActCarouselFullscreenBinding
import com.dimfcompany.nashprihodadmin.databinding.ActNewsAddEditBinding

class ActCarouselFullscreenMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActCarouselFullscreenMvp.Presenter>(), ActCarouselFullscreenMvp.MvpView
{
    val bnd_carouselFullscreen: ActCarouselFullscreenBinding

    init
    {
        bnd_carouselFullscreen = DataBindingUtil.inflate(layoutInflater, R.layout.act_carousel_fullscreen, parent, false)
        setRootView(bnd_carouselFullscreen.root)
    }

}
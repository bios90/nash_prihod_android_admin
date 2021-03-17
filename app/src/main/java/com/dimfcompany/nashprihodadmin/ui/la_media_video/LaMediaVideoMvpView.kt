package com.dimfcompany.nashprihodadmin.ui.la_media_video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.dp2px
import com.dimfcompany.nashprihodadmin.base.extensions.getNavbarHeight
import com.dimfcompany.nashprihodadmin.base.extensions.setHeight
import com.dimfcompany.nashprihodadmin.base.extensions.setPaddingsMy
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaMediaVideoBinding
import com.dimfcompany.nashprihodadmin.logic.utils.ExoVideoHelper
import com.google.android.exoplayer2.SimpleExoPlayer

class LaMediaVideoMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaMediaVideoMvp.Presenter>(), LaMediaVideoMvp.MvpView
{
    val bnd_media_video: LaMediaVideoBinding
    lateinit var player: SimpleExoPlayer

    init
    {
        bnd_media_video = DataBindingUtil.inflate(layoutInflater, R.layout.la_media_video, parent, false)
        setRootView(bnd_media_video.root)
    }

    override fun bindVideo(uri: String)
    {
        player = ExoVideoHelper.setPlayer(uri, bnd_media_video.playerView, false)
        bnd_media_video.playerView.player = player
    }

    override fun togglePlayPause(play: Boolean)
    {
        if (player.playWhenReady != play)
        {
            player.playWhenReady = play
        }
    }

    override fun toggleBottomNavbarPadding(have_padding: Boolean)
    {
        val height = if (have_padding) getNavbarHeight() else 0
        val view_fake_navbar: View = bnd_media_video.playerView.findViewById(R.id.view_fake_navbar)
        view_fake_navbar.setHeight(height)
    }
}
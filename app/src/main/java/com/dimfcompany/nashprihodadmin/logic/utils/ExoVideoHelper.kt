package com.dimfcompany.nashprihodadmin.logic.utils

import android.net.Uri
import android.os.Handler
import com.dimfcompany.nashprihodadmin.base.AppClass
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MediaSourceEventListener
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

object ExoVideoHelper
{
    fun getSourceFromUriUniversal(url: String): MediaSource
    {
        val factory = DefaultDataSourceFactory(AppClass.app, "ActVideoFullscreen")
        val progressive_source = ProgressiveMediaSource.Factory(factory).createMediaSource(Uri.parse(url))
        val looped_source = LoopingMediaSource(progressive_source)
        return looped_source
    }

    fun setPlayer(url: String, play: Boolean = true): SimpleExoPlayer
    {
        val source = getSourceFromUriUniversal(url)
        val player = ExoPlayerFactory.newSimpleInstance(AppClass.app, DefaultTrackSelector())
        player.prepare(source)
        player.playWhenReady = play
        return player
    }
}
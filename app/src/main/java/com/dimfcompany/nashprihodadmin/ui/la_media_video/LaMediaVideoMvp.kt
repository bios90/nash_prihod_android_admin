package com.dimfcompany.nashprihodadmin.ui.la_media_video

import android.view.View
import com.dimfcompany.nashprihodadmin.base.ViewWithTransition
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface LaMediaVideoMvp
{
    interface MvpView : BaseMvpView<Presenter>,ViewWithTransition
    {
        fun bindVideo(uri: String)
        fun togglePlayPause(play: Boolean)
        fun toggleBottomNavbarPadding(have_padding: Boolean)
        fun getPlayTime(): Long
        fun seekToTime(time:Long)
    }

    interface Presenter
    {
    }
}
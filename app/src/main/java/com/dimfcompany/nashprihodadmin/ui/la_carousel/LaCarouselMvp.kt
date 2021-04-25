package com.dimfcompany.nashprihodadmin.ui.la_carousel

import android.view.View
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface LaCarouselMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindViews(views: ArrayList<View>)
        fun setPosText(text: String)
        fun togglePosTextVisibility(is_visible: Boolean)
        fun scrollToPos(pos: Int)
        fun toggleBackgroundColor(color: Int)
        fun toggleFullscreenBtn(is_visible: Boolean)
        fun getCurrentPos():Int
    }

    interface Presenter
    {
        fun clickedFullScreen()
        fun tabChangedTo(pos: Int)
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_media_carousel

import android.view.View
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActCarouselMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindViews(views: ArrayList<View>)
        fun setPosText(text: String)
        fun togglePosTextVisibility(is_visible: Boolean)
        fun scrollToPos(pos: Int)
    }

    interface Presenter
    {
        fun tabChangedTo(pos: Int)
    }
}
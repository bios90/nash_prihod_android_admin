package com.dimfcompany.nashprihodadmin.ui.la_media_image

import android.view.View
import com.dimfcompany.nashprihodadmin.base.ViewWithTransition
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface LaMediaImageMvp
{
    interface MvpView : BaseMvpView<Presenter>,ViewWithTransition
    {
        fun bindImage(uri: String?)
        fun toggleModeZoomMode(is_touchable: Boolean)
        fun getViewForTransition(): View
    }

    interface Presenter
    {
    }
}
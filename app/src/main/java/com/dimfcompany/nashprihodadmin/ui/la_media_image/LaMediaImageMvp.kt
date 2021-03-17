package com.dimfcompany.nashprihodadmin.ui.la_media_image

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface LaMediaImageMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindImage(uri: String?)
        fun toggleModeZoomMode(is_touchable: Boolean)
    }

    interface Presenter
    {
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_news_add_edit

import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView

interface ActNewsAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtTitleText(): String?
        fun getEtTextText(): String?
        fun getTypePos(): Int?
        fun addMediaObj(obj: ObjWithMedia)
        fun scrollImagesToEnd()
        fun removeMediaAtPos(pos: Int)
    }

    interface Presenter
    {
        fun clickedMedia()
        fun clickedMediaObj(obj: ObjWithMedia)
        fun clickedRemoveMediaObject(obj: ObjWithMedia)
        fun clickedSave()
    }
}
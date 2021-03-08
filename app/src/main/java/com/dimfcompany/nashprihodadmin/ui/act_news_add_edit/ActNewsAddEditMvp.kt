package com.dimfcompany.nashprihodadmin.ui.act_news_add_edit

import com.dimfcompany.nashprihodadmin.base.ObjWithMedia
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews

interface ActNewsAddEditMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtTitleText(): String?
        fun getEtTextText(): String?
        fun getTypePos(): Int?
        fun addMediaObj(obj: ObjWithMedia)
        fun bindMediaObjs(objs: ArrayList<ObjWithMedia>)
        fun scrollImagesToEnd()
        fun removeMediaAtPos(pos: Int)
        fun bindNewsToEdit(news: ModelNews)
    }

    interface Presenter
    {
        fun clickedMedia()
        fun clickedMediaObj(obj: ObjWithMedia)
        fun clickedRemoveMediaObject(obj: ObjWithMedia)
        fun clickedSave()
    }
}
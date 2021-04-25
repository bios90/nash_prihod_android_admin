package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes

import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.models.ModelService

interface LaNotesMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindNotes(info: FeedDisplayInfo<ModelNote>)
    }

    interface Presenter
    {
        fun clickedAddNotes()
        fun clickedNote(note:ModelNote)
        fun swipedToRefresh()
        fun clickedFilterNotes()
    }
}
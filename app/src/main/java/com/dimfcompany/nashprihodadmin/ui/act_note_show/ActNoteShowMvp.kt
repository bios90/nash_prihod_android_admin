package com.dimfcompany.nashprihodadmin.ui.act_note_show

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote

interface ActNoteShowMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun bindNote(note: ModelNote)
    }

    interface Presenter
    {
        fun clickedReaded()
        fun clickedUser()
    }
}
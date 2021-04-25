package com.dimfcompany.nashprihodadmin.ui.act_note_add

import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpView
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice

interface ActNoteAddMvp
{
    interface MvpView : BaseMvpView<Presenter>
    {
        fun getEtNamesText(): String?
        fun bindPriceText(text: String)
        fun getForHealthPos():Int
    }

    interface Presenter
    {
        fun clickedSave()
        fun priceSelected(pos: Int)
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_filter_notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.enums.getPosForRb
import com.dimfcompany.nashprihodadmin.base.extensions.getCheckedPosition
import com.dimfcompany.nashprihodadmin.base.extensions.getNullableText
import com.dimfcompany.nashprihodadmin.base.extensions.setCheckedAtPos
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActFilterNotesBinding
import com.dimfcompany.nashprihodadmin.logic.models.FilterDataNotes

class ActFilterNotesMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActFilterNotesMvp.Presenter>(), ActFilterNotesMvp.MvpView
{
    val bnd_act_filter_notes: ActFilterNotesBinding

    init
    {
        bnd_act_filter_notes = DataBindingUtil.inflate(layoutInflater, R.layout.act_filter_notes, parent, false)
        setRootView(bnd_act_filter_notes.root)

        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_filter_notes.tvApply.setOnClickListener(
            {
                getPresenter().clickedApply()
            })
    }

    override fun bindFilterData(filter_data: FilterDataNotes)
    {
        bnd_act_filter_notes.etSearch.setText(filter_data.search_text)
        bnd_act_filter_notes.rgStatus.setCheckedAtPos(filter_data.status.getPosForRb())
    }

    override fun getEtSearchText(): String?
    {
        return bnd_act_filter_notes.etSearch.getNullableText()
    }

    override fun getStatusIndex(): Int
    {
        return bnd_act_filter_notes.rgStatus.getCheckedPosition()!!
    }
}
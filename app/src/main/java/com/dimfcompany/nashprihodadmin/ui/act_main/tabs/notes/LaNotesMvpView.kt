package com.dimfcompany.nashprihodadmin.ui.act_main.tabs.notes

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.EtFortmatterHelper
import com.dimfcompany.nashprihodadmin.base.adapters.AdapterRvNotes
import com.dimfcompany.nashprihodadmin.base.extensions.applyMyStyle
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.setDivider
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaNotesBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote

class LaNotesMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaNotesMvp.Presenter>(), LaNotesMvp.MvpView
{
    val bnd_la_notes: LaNotesBinding
    val adapter = AdapterRvNotes()

    init
    {
        bnd_la_notes = DataBindingUtil.inflate(layoutInflater, R.layout.la_notes, parent, false)
        setRootView(bnd_la_notes.root)

        initRecyclers()
        setListeners()
    }

    private fun setListeners()
    {
        bnd_la_notes.tvAddNote.setOnClickListener(
            {
                getPresenter().clickedAddNotes()
            })

        bnd_la_notes.srlNotes.setOnRefreshListener(
            {
                getPresenter().swipedToRefresh()
            })

        bnd_la_notes.tvFilter.setOnClickListener(
            {
                getPresenter().clickedFilterNotes()
            })
    }

    private fun initRecyclers()
    {
        bnd_la_notes.rvNotes.layoutManager = LinearLayoutManager(getRootView().context)
        bnd_la_notes.rvNotes.adapter = adapter
        bnd_la_notes.rvNotes.setDivider(getColorMy(R.color.transparent), dp2pxInt(8f))
        adapter.listener = (
                {
                    getPresenter().clickedNote(it)
                })

        bnd_la_notes.srlNotes.applyMyStyle()
    }

    override fun bindNotes(info: FeedDisplayInfo<ModelNote>)
    {
        bnd_la_notes.srlNotes.isRefreshing = false
        adapter.setItems(info)
    }
}
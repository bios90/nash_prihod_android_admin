package com.dimfcompany.nashprihodadmin.ui.act_user_show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimfcompany.akcsl.base.FeedDisplayInfo
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.adapters.AdapterRvNotesOfUser
import com.dimfcompany.nashprihodadmin.base.enums.TypeUserStatus
import com.dimfcompany.nashprihodadmin.base.extensions.applyMyStyle
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.setDivider
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActUserShowBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.dimfcompany.nashprihodadmin.ui.act_main.tabs.profile.bindUser

class ActUserShowMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActUserShowMvp.Presenter>(), ActUserShowMvp.MvpView
{
    val bnd_user_show: ActUserShowBinding
    override var view_overlay: View? = null
    val adapter = AdapterRvNotesOfUser()

    init
    {
        bnd_user_show = DataBindingUtil.inflate(layoutInflater, R.layout.act_user_show, parent, false)
        setRootView(bnd_user_show.root)
        view_overlay = bnd_user_show.viewOverlay

        setListeners()
        initRecyclers()
    }


    private fun setListeners()
    {
        bnd_user_show.tvEdit.setOnClickListener(
            {
                getPresenter().clickedEdit()
            })

        bnd_user_show.tvBlock.setOnClickListener(
            {
                getPresenter().clickedChangeStatus(TypeUserStatus.BANNED)
            })

        bnd_user_show.tvMakeActive.setOnClickListener(
            {
                getPresenter().clickedChangeStatus(TypeUserStatus.ACTIVE)
            })

        bnd_user_show.tvUnblock.setOnClickListener(
            {
                getPresenter().clickedChangeStatus(TypeUserStatus.ACTIVE)
            })

        bnd_user_show.lalAboutUser.cvAvatar.imgImg.setOnClickListener(
            {
                getPresenter().clickedAvatar()
            })
    }

    override fun bindUser(user: ModelUser)
    {
        bnd_user_show.lalAboutUser.bindUser(user)

        bindStatuses(user)
    }

    override fun bindNotes(info: FeedDisplayInfo<ModelNote>)
    {
        adapter.setItems(info)
    }

    private fun initRecyclers()
    {
        bnd_user_show.recNotes.layoutManager = LinearLayoutManager(getRootView().context)
        bnd_user_show.recNotes.adapter = adapter
        bnd_user_show.recNotes.setDivider(getColorMy(R.color.transparent), dp2pxInt(8f))
        adapter.listener = (
                {
                    getPresenter().clickedNote(it)
                })
    }

    private fun bindStatuses(user: ModelUser)
    {
        val status = user.status ?: return

        if (status == TypeUserStatus.WAIT_ADMIN_APPROVE)
        {
            bnd_user_show.tvBlock.visibility = View.VISIBLE
            bnd_user_show.tvUnblock.visibility = View.GONE
            bnd_user_show.tvMakeActive.visibility = View.VISIBLE
        }
        else if (status == TypeUserStatus.ACTIVE)
        {
            bnd_user_show.tvBlock.visibility = View.VISIBLE
            bnd_user_show.tvUnblock.visibility = View.GONE
            bnd_user_show.tvMakeActive.visibility = View.GONE
        }
        else if (status == TypeUserStatus.BANNED)
        {
            bnd_user_show.tvBlock.visibility = View.GONE
            bnd_user_show.tvUnblock.visibility = View.VISIBLE
            bnd_user_show.tvMakeActive.visibility = View.GONE
        }
    }
}
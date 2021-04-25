package com.dimfcompany.nashprihodadmin.ui.act_note_show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.adapters.bindNote
import com.dimfcompany.nashprihodadmin.base.enums.TypeNoteStatus
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActNoteShowBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatAsMoney
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.rucode.autopass.logic.utils.images.GlideManager

class ActNoteShowMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActNoteShowMvp.Presenter>(), ActNoteShowMvp.MvpView
{
    val bnd_note_show: ActNoteShowBinding

    init
    {
        bnd_note_show = DataBindingUtil.inflate(layoutInflater, R.layout.act_note_show, parent, false)
        setRootView(bnd_note_show.root)

        setListeners()
    }

    private fun setListeners()
    {
        bnd_note_show.tvReaded.setOnClickListener(
            {
                getPresenter().clickedReaded()
            })

        bnd_note_show.larAuthor.setOnClickListener(
            {
                getPresenter().clickedUser()
            })
    }

    override fun bindNote(note: ModelNote)
    {
        bnd_note_show.tvForHealthType.setTextColor(note.getColorForHealthType())
        bnd_note_show.tvForHealthType.text = note.getTextForHealthType()

        val color_status = note.status?.getColor() ?: getColorMy(R.color.gray5)
        bnd_note_show.tvStatus.setTextColor(color_status)
        bnd_note_show.tvStatus.text = note.getStatusNameForDisplay()

        val text_donation = (note.donation_sum ?: 0.0).formatAsMoney() + " р."
        bnd_note_show.tvDonation.text = text_donation

        GlideManager.loadImage(bnd_note_show.cvAvatar.imgImg, note.user?.avatar?.url,show_failed_images = false)
        bnd_note_show.tvUserName.text = note.user?.getFullName()
        bnd_note_show.tvDate.text = note.updated?.formatToString(DateManager.FORMAT_FOR_DISPLAY_WITH_TIME)

        bnd_note_show.tvNames.text = note.names?.replace("*", "\n")
        bnd_note_show.tvReaded.visibility = (note.status != TypeNoteStatus.READED).toVisibility()
    }
}
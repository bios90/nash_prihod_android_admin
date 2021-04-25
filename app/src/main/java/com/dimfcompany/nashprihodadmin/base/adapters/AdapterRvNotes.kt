package com.dimfcompany.nashprihodadmin.base.adapters

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.diff.BaseDiff
import com.dimfcompany.nashprihodadmin.base.diff.DiffNotes
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getStringMy
import com.dimfcompany.nashprihodadmin.databinding.ItemNoteBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatAsMoney
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.rucode.autopass.logic.utils.images.GlideManager

class AdapterRvNotes : BaseRvAdapter<ModelNote, ItemNoteBinding>()
{
    override val layout_id: Int = R.layout.item_note
    override val diff_class: Class<out BaseDiff<ModelNote>> = DiffNotes::class.java

    override fun onBindViewHolder(holder: BaseViewHolder<ModelNote, ItemNoteBinding>, position: Int)
    {
        val item = items.get(position)
        holder.bnd.bindNote(item)
        holder.bnd.root.setOnClickListener(
            {
                listener?.invoke(item)
            })
    }
}

fun ItemNoteBinding.bindNote(note: ModelNote)
{
    this.tvForHealthType.setTextColor(note.getColorForHealthType())
    this.tvForHealthType.text = note.getTextForHealthType()

    val color_status = note.status?.getColor() ?: getColorMy(R.color.gray5)
    this.tvStatus.setTextColor(color_status)
    this.tvStatus.text = note.getStatusNameForDisplay()

    this.tvNames.text = note.getNamesString()
    GlideManager.loadImage(this.cvAvatar.imgImg, note.user?.avatar?.url,show_failed_images = false)
    this.tvUserName.text = note.user?.getFullName()
    this.tvDate.text = note.updated?.formatToString(DateManager.FORMAT_FOR_DISPLAY_WITH_TIME)

    this.tvDonation.text = note.getPriceText()
}
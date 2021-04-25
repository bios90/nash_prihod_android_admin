package com.dimfcompany.nashprihodadmin.base.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.diff.BaseDiff
import com.dimfcompany.nashprihodadmin.base.diff.DiffNotes
import com.dimfcompany.nashprihodadmin.databinding.ItemNoteBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemNoteOfUserBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNote
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString

class AdapterRvNotesOfUser : BaseRvAdapter<ModelNote, ItemNoteOfUserBinding>()
{
    override val layout_id: Int = R.layout.item_note_of_user
    override val diff_class: Class<out BaseDiff<ModelNote>> = DiffNotes::class.java

    override fun onBindViewHolder(holder: BaseViewHolder<ModelNote, ItemNoteOfUserBinding>, position: Int)
    {
        val item = items.get(position)
        holder.bnd.bindNote(item)
        holder.bnd.root.setOnClickListener(
            {
                listener?.invoke(item)
            })
    }
}

fun ItemNoteOfUserBinding.bindNote(note: ModelNote)
{
    this.tvDate.text = note.created?.formatToString()
    this.tvForHealthType.text = note.getTextForHealthType()
    this.tvForHealthType.setTextColor(note.getColorForHealthType())
    this.tvPrice.text = note.getPriceText()
    this.tvNames.text = note.getNamesString()
}
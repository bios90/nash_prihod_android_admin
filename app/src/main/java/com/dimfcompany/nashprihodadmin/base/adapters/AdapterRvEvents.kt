package com.dimfcompany.nashprihodadmin.base.adapters

import android.view.View
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.diff.BaseDiff
import com.dimfcompany.nashprihodadmin.base.diff.DiffEvents
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.databinding.ItemEventBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.rucode.autopass.logic.utils.images.GlideManager

class AdapterRvEvents : BaseRvAdapter<ModelNews, ItemEventBinding>()
{
    override val layout_id: Int = R.layout.item_event
    override val diff_class: Class<out BaseDiff<ModelNews>> = DiffEvents::class.java

    override fun onBindViewHolder(holder: BaseViewHolder<ModelNews, ItemEventBinding>, position: Int)
    {
        val item = items.get(position)
        holder.bnd.bindNotice(item)
        holder.bnd.lalCard.setOnClickListener(
            {
                listener?.invoke(item)
            })
    }
}

fun ItemEventBinding.bindNotice(news: ModelNews)
{
    val first_media = news.media_objs?.getOrNull(0)
    if (first_media != null)
    {
        GlideManager.loadImage(this.img, first_media.preview_url)
        this.cardImg.visibility = View.VISIBLE
        this.tvPlay.visibility = (first_media.type == TypeMedia.VIDEO).toVisibility()
    }
    else
    {
        this.cardImg.visibility = View.GONE
    }

    this.tvType.text = news.type?.getNameForDisplay()
    this.tvType.background = news.type?.getBgBubble()

    this.tvTitle.text = news.title
    this.tvText.text = news.text
    this.tvDate.text = news.updated?.formatToString()
    this.tvAuthorName.text = news.author?.getFullName()
}
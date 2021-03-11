package com.dimfcompany.nashprihodadmin.base.adapters

import android.view.View
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.diff.BaseDiff
import com.dimfcompany.nashprihodadmin.base.diff.DiffEvents
import com.dimfcompany.nashprihodadmin.base.diff.DiffNotices
import com.dimfcompany.nashprihodadmin.base.enums.TypeMedia
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.databinding.ItemEventBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemNoticeBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.rucode.autopass.logic.utils.images.GlideManager

class AdapterRvNotices : BaseRvAdapter<ModelNotice, ItemNoticeBinding>()
{
    override val layout_id: Int = R.layout.item_notice
    override val diff_class: Class<out BaseDiff<ModelNotice>> = DiffNotices::class.java

    override fun onBindViewHolder(holder: BaseViewHolder<ModelNotice, ItemNoticeBinding>, position: Int)
    {
        val item = items.get(position)
        holder.bnd.bindNews(item)
        holder.bnd.root.setOnClickListener(
            {
                listener?.invoke(item)
            })
    }
}

fun ItemNoticeBinding.bindNews(news: ModelNotice)
{
    this.tvTitle.text = news.title
    this.tvText.text = news.text
    this.tvDate.text = news.updated?.formatToString()
}
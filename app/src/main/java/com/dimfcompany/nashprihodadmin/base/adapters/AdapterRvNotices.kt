package com.dimfcompany.nashprihodadmin.base.adapters

import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.diff.BaseDiff
import com.dimfcompany.nashprihodadmin.base.diff.DiffNotices
import com.dimfcompany.nashprihodadmin.databinding.ItemNoticeBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNotice
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString

class AdapterRvNotices : BaseRvAdapter<ModelNotice, ItemNoticeBinding>()
{
    override val layout_id: Int = R.layout.item_notice
    override val diff_class: Class<out BaseDiff<ModelNotice>> = DiffNotices::class.java

    override fun onBindViewHolder(holder: BaseViewHolder<ModelNotice, ItemNoticeBinding>, position: Int)
    {
        val item = items.get(position)
        holder.bnd.bindNotice(item)
        holder.bnd.root.setOnClickListener(
            {
                listener?.invoke(item)
            })
    }
}

fun ItemNoticeBinding.bindNotice(notice: ModelNotice)
{
    this.tvTitle.text = notice.title
    this.tvText.text = notice.text
    this.tvDate.text = notice.updated?.formatToString()
}

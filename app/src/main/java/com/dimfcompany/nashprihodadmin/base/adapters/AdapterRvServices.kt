package com.dimfcompany.nashprihodadmin.base.adapters

import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.diff.BaseDiff
import com.dimfcompany.nashprihodadmin.base.diff.DiffService
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.toVisibility
import com.dimfcompany.nashprihodadmin.databinding.ItemServiceBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString
import com.dimfcompany.nashprihodadmin.logic.utils.isToday

class AdapterRvServices : BaseRvAdapter<ModelService, ItemServiceBinding>()
{
    override val layout_id: Int = R.layout.item_service
    override val diff_class: Class<out BaseDiff<ModelService>> = DiffService::class.java

    override fun onBindViewHolder(holder: BaseViewHolder<ModelService, ItemServiceBinding>, position: Int)
    {
        val service = items.get(position)
        holder.bnd.bindService(service)

        holder.bnd.root.setOnClickListener(
            {
                listener?.invoke(service)
            })
    }
}

fun ItemServiceBinding.bindService(service: ModelService)
{
    this.tvDate.text = service.date?.formatToString()
    this.tvTitle.text = service.title
    this.tvAuthor.text = service.author?.getFullName()

    for (i in 0..5)
    {
        val la = this.lalForTimes.getChildAt(i) as? ViewGroup ?: continue
        val time = service.service_times?.getOrNull(i)

        val tv_time = la.getChildAt(0) as TextView
        val tv_text = la.getChildAt(1) as TextView

        tv_time.text = time?.time?.formatToString(DateManager.FORMAT_FOR_TIME)
        tv_text.text = time?.text

        la.visibility = (time != null).toVisibility()
    }

    val is_white_mode = this.tvDate.currentTextColor == getColorMy(R.color.blue)
    val is_service_today = service.date?.isToday() == true

    if (is_service_today && is_white_mode == true)
    {
        makeBlue()
    }
    else if (!is_service_today && is_white_mode == false)
    {
        makeWhite()
    }
}

fun ItemServiceBinding.makeWhite()
{
    this.card.setCardBackgroundColor(getColorMy(R.color.white))
    this.tvDate.setTextColor(getColorMy(R.color.blue))
    this.tvTitle.setTextColor(getColorMy(R.color.gray7))

    for (i in 0..5)
    {
        val la = this.lalForTimes.getChildAt(i) as? ViewGroup ?: continue

        val tv_time = la.getChildAt(0) as TextView
        val tv_text = la.getChildAt(1) as TextView

        tv_time.setTextColor(getColorMy(R.color.blue))
        tv_text.setTextColor(getColorMy(R.color.gray6))
    }

    this.tvAuthor.setTextColor(getColorMy(R.color.blue))
}

fun ItemServiceBinding.makeBlue()
{
    this.card.setCardBackgroundColor(getColorMy(R.color.blue))
    this.tvDate.setTextColor(getColorMy(R.color.white))
    this.tvTitle.setTextColor(getColorMy(R.color.white))

    for (i in 0..5)
    {
        val la = this.lalForTimes.getChildAt(i) as? ViewGroup ?: continue

        val tv_time = la.getChildAt(0) as TextView
        val tv_text = la.getChildAt(1) as TextView

        tv_time.setTextColor(getColorMy(R.color.white))
        tv_text.setTextColor(getColorMy(R.color.white))
    }

    this.tvAuthor.setTextColor(getColorMy(R.color.white))
}
package com.dimfcompany.nashprihodadmin.ui.act_service_show

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.dp2pxInt
import com.dimfcompany.nashprihodadmin.base.extensions.setHeight
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActServiceShowBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemServiceTextToShowBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemServiceTimeBinding
import com.dimfcompany.nashprihodadmin.databinding.ItemServiceTimeNoShadowBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelService
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString

class ActServiceShowMvpView(val layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActServiceShowMvp.Presenter>(), ActServiceShowMvp.MvpView
{
    val bnd_service_show: ActServiceShowBinding

    init
    {
        bnd_service_show = DataBindingUtil.inflate(layoutInflater, R.layout.act_service_show, parent, false)
        setRootView(bnd_service_show.root)
    }

    override fun bindService(service: ModelService)
    {
        bnd_service_show.tvDate.text = service.date?.formatToString(DateManager.FORMAT_DATE_WITH_WEEK_DAY)

        for (time in service.service_times ?: arrayListOf())
        {
            val bnd_time: ItemServiceTimeNoShadowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_service_time_no_shadow, bnd_service_show.laForServiceTimes, false)
            bnd_time.tvTime.text = time.time?.formatToString(DateManager.FORMAT_FOR_TIME)
            bnd_time.tvTitle.text = time.text
            bnd_time.root.setHeight(dp2pxInt(20))
            bnd_service_show.laForServiceTimes.addView(bnd_time.root)
        }

        bnd_service_show.tvTitle.text = service.title

        for (text in service.service_texts ?: arrayListOf())
        {
            val bnd_text: ItemServiceTextToShowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_service_text_to_show, bnd_service_show.laForServiceTexts, false)
            bnd_text.tvTitle.text = text.title
            bnd_text.tvText.text = text.text
            bnd_service_show.laForServiceTexts.addView(bnd_text.root)
        }
    }
}
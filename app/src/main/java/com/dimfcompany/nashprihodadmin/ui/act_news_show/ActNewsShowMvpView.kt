package com.dimfcompany.nashprihodadmin.ui.act_news_show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dimfcompany.akcsl.base.adapters.AdapterVpUniversal
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActNewsShowBinding
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.formatToString

class ActNewsShowMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActNewsShowMvp.Presenter>(), ActNewsShowMvp.MvpView
{
    val bnd_news_show: ActNewsShowBinding
    private val adapter_vp = AdapterVpUniversal()

    init
    {
        bnd_news_show = DataBindingUtil.inflate(layoutInflater, R.layout.act_news_show, parent, false)
        setRootView(bnd_news_show.root)

        setViewPager()
    }

    private fun setViewPager()
    {
        bnd_news_show.vp.adapter = adapter_vp
    }

    override fun bindViews(views: ArrayList<View>)
    {
        adapter_vp.setViews(views)
    }

    override fun bindNews(news: ModelNews)
    {
        bnd_news_show.tvAuthor.text = news.author?.getFullName()
        bnd_news_show.tvType.text = news.type?.getNameForDisplay()
        bnd_news_show.tvType.background = news.type?.getBgBubble()
        bnd_news_show.tvTitle.text = news.title
        bnd_news_show.tvText.text = news.text
        bnd_news_show.tvDate.text = news.created?.formatToString(DateManager.FORMAT_FOR_DISPLAY_FULL_MONTH)
    }
}
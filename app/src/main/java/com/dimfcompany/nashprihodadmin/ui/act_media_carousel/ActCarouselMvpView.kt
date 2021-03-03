package com.dimfcompany.nashprihodadmin.ui.act_media_carousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.dimfcompany.akcsl.base.adapters.AdapterVpUniversal
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActMediaCarouselBinding

class ActCarouselMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActCarouselMvp.Presenter>(), ActCarouselMvp.MvpView
{
    val bnd_act_carousel: ActMediaCarouselBinding

    private val adapter_vp = AdapterVpUniversal()

    init
    {
        bnd_act_carousel = DataBindingUtil.inflate(layoutInflater, R.layout.act_media_carousel, parent, false)
        setRootView(bnd_act_carousel.root)
        setViewPager()
        setupViews()

        setListeners()
    }

    private fun setListeners()
    {
        bnd_act_carousel.vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener
        {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
            {

            }

            override fun onPageSelected(position: Int)
            {
                getPresenter().tabChangedTo(position)
            }

            override fun onPageScrollStateChanged(state: Int)
            {
            }
        })
    }

    private fun setViewPager()
    {
        bnd_act_carousel.vp.adapter = adapter_vp
    }

    private fun setupViews()
    {
        val margint_top = getStatusBarHeight() + dp2pxInt(12)
        bnd_act_carousel.tvCurrentPos.setMargins(null, margint_top, null, null)
    }

    override fun togglePosTextVisibility(is_visible: Boolean)
    {
        if (is_visible)
        {
            bnd_act_carousel.tvCurrentPos.animateFadeOut()
        }
        else
        {
            bnd_act_carousel.tvCurrentPos.animateFadeIn()
        }
    }

    override fun bindViews(views: ArrayList<View>)
    {
        adapter_vp.setViews(views)
        bnd_act_carousel.vp.offscreenPageLimit = views.size
    }

    override fun setPosText(text: String)
    {
        bnd_act_carousel.tvCurrentPos.text = text
    }

    override fun scrollToPos(pos: Int)
    {
        bnd_act_carousel.vp.setCurrentItem(pos, true)
    }
}
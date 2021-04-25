package com.dimfcompany.nashprihodadmin.ui.la_carousel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.dimfcompany.akcsl.base.adapters.AdapterVpUniversal
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.extensions.*
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.LaCarouselBinding

class LaCarouselMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<LaCarouselMvp.Presenter>(), LaCarouselMvp.MvpView
{
    val bnd_act_carousel: LaCarouselBinding

    private val adapter_vp = AdapterVpUniversal()

    init
    {
        bnd_act_carousel = DataBindingUtil.inflate(layoutInflater, R.layout.la_carousel, parent, false)
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

        bnd_act_carousel.tvFullScreen.setOnClickListener(
            {
                getPresenter().clickedFullScreen()
            })
    }

    private fun setViewPager()
    {
        bnd_act_carousel.vp.adapter = adapter_vp
    }

    private fun setupViews()
    {
        val margin_top = getStatusBarHeight() + dp2pxInt(12)

        bnd_act_carousel.tvCurrentPos.setMargins(null, margin_top, null, null)
        bnd_act_carousel.tvFullScreen.setMargins(null, margin_top, null, null)
    }

    override fun toggleFullscreenBtn(is_visible: Boolean)
    {
        bnd_act_carousel.tvFullScreen.visibility = is_visible.toVisibility()
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

    override fun toggleBackgroundColor(color: Int)
    {
        bnd_act_carousel.larRoot.setBackgroundColor(color)
    }

    override fun getCurrentPos(): Int
    {
        return bnd_act_carousel.vp.currentItem
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.dimfcompany.akcsl.base.adapters.AdapterVpUniversal
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.base.extensions.getTypeFaceFromResource
import com.dimfcompany.nashprihodadmin.base.mvpview.BaseMvpViewImpl
import com.dimfcompany.nashprihodadmin.databinding.ActMainBinding
import com.dimfcompany.nashprihodadmin.logic.utils.builders.BuilderBg

class ActMainMvpView(layoutInflater: LayoutInflater, parent: ViewGroup?)
    : BaseMvpViewImpl<ActMainMvp.Presenter>(), ActMainMvp.MvpView
{
    val bnd_act_main: ActMainBinding
    val la_bottom: LinearLayout
    private val adapter_vp = AdapterVpUniversal()

    init
    {
        bnd_act_main = DataBindingUtil.inflate(layoutInflater, R.layout.act_main, parent, false)
        la_bottom = bnd_act_main.laBottomNav.root as LinearLayout
        setRootView(bnd_act_main.root)

        setViewPager()
        setBottomBarButtonsBg()
        setListeners()
    }

    private fun setBottomBarButtonsBg()
    {
        la_bottom.children.forEachIndexed(
            { index, view ->

                val builder_bg = BuilderBg()
                        .setView(view)
                        .isRipple(true)
                        .setBgColor(getColorMy(R.color.transparent))
                        .setRippleColor(getColorMy(R.color.blue_trans_50))
                        .isDpMode(true)


                if (view == la_bottom.children.first())
                {
                    builder_bg.setCornerRadiuses(16f, 0f, 0f, 0f)
                }
                else if (view == la_bottom.children.last())
                {
                    builder_bg.setCornerRadiuses(0f, 16f, 0f, 0f)
                }

                Log.e("ActMainMvpView", "setBottomBarButtonsBg: Apllaying bg to view")

                builder_bg.applyToView()
            })
    }

    private fun setListeners()
    {
        la_bottom.children.forEachIndexed(
            { index, view ->

                view.setOnClickListener(
                    {
                        val tab = TypeTab.initFromPos(index)
                        getPresenter().clickedTab(tab)
                    })
            })

        bnd_act_main.vpMain
                .addOnPageChangeListener(object : ViewPager.OnPageChangeListener
                {
                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
                    {

                    }

                    override fun onPageSelected(position: Int)
                    {
                        val tab = TypeTab.initFromPos(position)
                        getPresenter().clickedTab(tab)
                    }

                    override fun onPageScrollStateChanged(state: Int)
                    {

                    }
                })
    }

    private fun setViewPager()
    {
        bnd_act_main.vpMain.adapter = adapter_vp
        bnd_act_main.vpMain.offscreenPageLimit = 5
    }

    override fun setViews(views: ArrayList<View>)
    {
        adapter_vp.setViews(views)
    }

    override fun scrollToTab(tab: TypeTab)
    {
        val type_tab_pos = tab.getPos()
        if (type_tab_pos != bnd_act_main.vpMain.currentItem)
        {
            bnd_act_main.vpMain.setCurrentItem(type_tab_pos, true)
        }

        changeColorsOfTabs(tab)
    }

    private fun changeColorsOfTabs(tab: TypeTab)
    {
        val lar_bottom_nav = bnd_act_main.laBottomNav.root as LinearLayout
        val tabs_count = lar_bottom_nav.childCount
        val color_blue = getColorMy(R.color.blue)
        val color_gray_5 = getColorMy(R.color.gray5)

        val font_faw_light = getTypeFaceFromResource(R.font.fa_light)
        val font_faw_reg = getTypeFaceFromResource(R.font.fa_regular)

        val font_reg = getTypeFaceFromResource(R.font.rubik_reg)
        val font_bold = getTypeFaceFromResource(R.font.rubik_bold)

        for (i in 0 until tabs_count)
        {
            val lal = lar_bottom_nav.getChildAt(i) as ViewGroup
            val tv_faw = lal.getChildAt(0) as TextView
            val tv_text = lal.getChildAt(1) as TextView

            if (tab.getPos() == i)
            {
                tv_faw.setTextColor(color_blue)
                tv_text.setTextColor(color_blue)

                tv_faw.setTypeface(font_faw_reg)
                tv_text.setTypeface(font_bold)
            }
            else
            {
                tv_faw.setTextColor(color_gray_5)
                tv_text.setTextColor(color_gray_5)

                tv_faw.setTypeface(font_faw_light)
                tv_text.setTypeface(font_reg)
            }
        }
    }
}
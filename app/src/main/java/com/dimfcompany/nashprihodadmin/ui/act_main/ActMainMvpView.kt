package com.dimfcompany.nashprihodadmin.ui.act_main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimfcompany.akcsl.base.adapters.AdapterVpUniversal
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.enums.TypeTab
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
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
}
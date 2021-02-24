package com.dimfcompany.nashprihodadmin.ui.act_test

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.base.BaseActivity
import com.dimfcompany.nashprihodadmin.base.extensions.getColorMy
import com.dimfcompany.nashprihodadmin.databinding.ActTestBinding

class ActTest : BaseActivity()
{

    lateinit var bnd_act_test: ActTestBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        setNavStatus()
        super.onCreate(savedInstanceState)
        bnd_act_test = DataBindingUtil.setContentView(this, R.layout.act_test)
    }

    fun setNavStatus()
    {
        is_full_screen = false
        is_below_nav_bar = false
        color_status_bar = getColorMy(R.color.green)
        color_nav_bar = getColorMy(R.color.blue)
        is_light_status_bar = false
        is_light_nav_bar = false
    }
}
package com.dimfcompany.nashprihodadmin.ui.act_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dimfcompany.nashprihodadmin.R
import com.dimfcompany.nashprihodadmin.databinding.ActTest2Binding
import com.dimfcompany.nashprihodadmin.databinding.ActTestBinding

class ActTest2 : AppCompatActivity()
{
    lateinit var bnd_act_test: ActTest2Binding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        bnd_act_test = DataBindingUtil.setContentView(this, R.layout.act_test_2)

    }
}
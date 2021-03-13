package com.dimfcompany.nashprihodadmin.base

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dimfcompany.nashprihodadmin.base.adapters.doubleString
import com.dimfcompany.nashprihodadmin.di.ComponentApp
import com.dimfcompany.nashprihodadmin.di.DaggerComponentApp
import com.dimfcompany.nashprihodadmin.logic.models.ModelFile
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import com.dimfcompany.nashprihodadmin.logic.models.ObjWithMediaDeserializer
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AppClass : DaggerApplication()
{
    companion object
    {
        lateinit var app: AppClass
        var app_component: ComponentApp = DaggerComponentApp.builder().build()
        lateinit var gson: Gson
        var top_activity: AppCompatActivity? = null
    }

    override fun onCreate()
    {
        super.onCreate()
        app = this
        gson = GsonBuilder()
                .setDateFormat(DateManager.FORMAT_FOR_SERVER_LARAVEL)
                .registerTypeAdapter(ObjWithMedia::class.java, ObjWithMediaDeserializer())
                .create()
        test()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
    {
        return app_component
    }
    fun test()
    {
        val str = "sfdxfgnghm"
        Log.e("APP", str.doubleString() )
    }
}

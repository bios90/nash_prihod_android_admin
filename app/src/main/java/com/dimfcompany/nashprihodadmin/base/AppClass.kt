package com.dimfcompany.nashprihodadmin.base

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dimfcompany.nashprihodadmin.base.extensions.initEnumFromString
import com.dimfcompany.nashprihodadmin.di.ComponentApp
import com.dimfcompany.nashprihodadmin.di.DaggerComponentApp
import com.dimfcompany.nashprihodadmin.logic.models.ObjWithMediaDeserializer
import com.dimfcompany.nashprihodadmin.logic.utils.DateManager
import com.dimfcompany.nashprihodadmin.logic.utils.TypePush
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
        val type = initEnumFromString<TypePush>("new_user_register")
        Log.e("AppClass", "test: Type of push is $type")

//        val user = ModelUser.getTestUser()
//        Log.e("AppClass", "test: USer email is ${user.email}")
//        val user_str = SharedPrefsManager.pref_current_user.get().value?.toJsonMy()
//        Log.e("AppClass", "test: user str is \n\n$user_str")
    }
}

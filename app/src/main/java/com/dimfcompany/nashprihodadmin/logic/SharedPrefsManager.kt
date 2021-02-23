package com.dimfcompany.nashprihodadmin.logic

import android.content.Context
import com.dimfcompany.nashprihodadmin.base.AppClass
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.Optional
import com.dimfcompany.nashprihodadmin.base.extensions.asOptional
import com.dimfcompany.nashprihodadmin.base.extensions.toJsonMy
import com.dimfcompany.nashprihodadmin.logic.models.ModelUser
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences

object SharedPrefsManager
{
    private val prefs = AppClass.app.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)
    private val rx_prefs = RxSharedPreferences.create(prefs)

    val pref_fb_token = rx_prefs.getObject("fb_token", Optional(null), GlobalPrefsAdapter(String::class.java))
    val pref_current_user = rx_prefs.getObject("current_user", Optional(null), GlobalPrefsAdapter(ModelUser::class.java))
}

class GlobalPrefsAdapter<T>(private val clazz: Class<T>) : Preference.Converter<Optional<T>>
{
    override fun deserialize(serialized: String): Optional<T>
    {
        return AppClass.gson.fromJson(serialized, clazz).asOptional()
    }

    override fun serialize(value: Optional<T>): String
    {
        if (value.value == null)
        {
            return "null"
        }
        return value.value.toJsonMy()!!
    }
}
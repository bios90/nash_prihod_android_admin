package com.dimfcompany.nashprihodadmin.networking

import com.dimfcompany.nashprihodadmin.logic.SharedPrefsManager
import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        var request = chain.request()
        //Todo later make optional
        val author_id = SharedPrefsManager.pref_current_user.get().value?.id ?: 1
        val http_url = request.url.newBuilder().addQueryParameter("author_id", author_id.toString()).build()
        request = request.newBuilder().url(http_url).build();
        return chain.proceed(request);
    }
}
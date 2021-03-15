package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService
{
    @FormUrlEncoded
    @POST(Constants.Urls.INSERT_SERVICE_TIME)
    suspend fun insertServiceTime(
            @Field("time") time: String,
            @Field("text") text: String,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.Urls.INSERT_SERVICE_TEXT)
    suspend fun insertServiceText(
            @Field("title") title: String,
            @Field("text") text: String,
    ): Response<ResponseBody>
}
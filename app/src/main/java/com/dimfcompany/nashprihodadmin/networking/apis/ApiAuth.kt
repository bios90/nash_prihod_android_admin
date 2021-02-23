package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.extensions.getDeviceName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiAuth
{
    @Multipart
    @POST(Constants.Urls.REGISTER)
    suspend fun register(
            @PartMap map_params: Map<String, String?>,
            @Part avatar: MultipartBody.Part?
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.Urls.LOGIN)
    suspend fun login(
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("fb_token") fb_token: String?,
            @Field("env") env: String = "android",
            @Field("device_name") device_name: String? = getDeviceName()
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.Urls.FORGOT_PASS)
    suspend fun forgotPass(
            @Field("email") email: String
    ): Response<ResponseBody>
}
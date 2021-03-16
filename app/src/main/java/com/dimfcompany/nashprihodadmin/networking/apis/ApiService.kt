package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST(Constants.Urls.INSERT_SERVICE)
    suspend fun uspertService(
            @Field("service_id") service_id: Long?,
            @Field("title") title: String,
            @Field("date") date: String,
            @Field("service_times_ids") service_times_ids: String,
            @Field("service_texts_ids") service_texts_ids: String?
    ): Response<ResponseBody>

    @GET(Constants.Urls.GET_SERVICES)
    suspend fun getServices(): Response<ResponseBody>

    @GET(Constants.Urls.GET_SERVICE_BY_ID)
    suspend fun getServiceById(@Query("service_id") service_id: Long): Response<ResponseBody>

    @FormUrlEncoded
    @POST(Constants.Urls.DELETE_SERVICE)
    suspend fun deleteService(@Field("service_id") service_id: Long): Response<ResponseBody>
}
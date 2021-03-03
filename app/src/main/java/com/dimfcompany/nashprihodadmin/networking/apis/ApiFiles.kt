package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiFiles
{
    @Multipart
    @POST(Constants.Urls.UPLOAD_IMAGE)
    suspend fun uploadImage(
            @Part file: MultipartBody.Part?
    ): Response<ResponseBody>

    @Multipart
    @POST(Constants.Urls.UPLOAD_VIDEO)
    suspend fun uploadVideo(
            @PartMap map_params: Map<String, String?>,
            @Part file: MultipartBody.Part?
    ): Response<ResponseBody>
}
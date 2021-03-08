package com.dimfcompany.nashprihodadmin.networking.apis

import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.base.enums.TypeNews
import com.dimfcompany.nashprihodadmin.logic.models.ModelNews
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiNews
{
    @FormUrlEncoded
    @POST(Constants.Urls.INSERT_OR_UPDATE_NEWS)
    suspend fun insertOrUpdateNews(
            @Field("news_id") news_id: Long?,
            @Field("title") title: String,
            @Field("text") text: String,
            @Field("type") type: String,
            @Field("media_ids") media_ids_str: String?
    ): Response<ResponseBody>

    @GET(Constants.Urls.GET_NEWS)
    suspend fun getNews(): Response<ResponseBody>

    @GET(Constants.Urls.GET_NEWS_BY_ID)
    suspend fun getNewsById(@Query("news_id") news_id: Long): Response<ResponseBody>
}

suspend fun ApiNews.makeInsertOrUpdateNews(news: ModelNews, media_ids: ArrayList<Long>?): Response<ResponseBody>
{
    val media_ids_str = media_ids?.map({ it.toString() })?.joinToString("-")
    return insertOrUpdateNews(news.id, news.title!!, news.text!!, news.type!!.getNameForServer(), media_ids_str)
}
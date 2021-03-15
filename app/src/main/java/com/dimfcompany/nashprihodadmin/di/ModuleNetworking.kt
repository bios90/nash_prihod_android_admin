package com.dimfcompany.nashprihodadmin.di

import com.dimfcompany.nashprihodadmin.base.AppClass
import com.dimfcompany.nashprihodadmin.base.Constants
import com.dimfcompany.nashprihodadmin.networking.MyInterceptor
import com.dimfcompany.nashprihodadmin.networking.apis.*
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ModuleNetworking
{
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor
    {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun provideCurlInterceptor(): CurlLoggerInterceptor
    {
        return CurlLoggerInterceptor("****curl****")
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(log_interceptor: HttpLoggingInterceptor, curl_interceptor: CurlLoggerInterceptor): OkHttpClient
    {
        val httpClientBuilder = OkHttpClient.Builder()
//        httpClientBuilder.addInterceptor(log_interceptor)
        httpClientBuilder.addInterceptor(MyInterceptor())
        httpClientBuilder.addInterceptor(curl_interceptor)
        httpClientBuilder.callTimeout(120, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(120, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(120, TimeUnit.SECONDS)

        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit
    {
        return Retrofit.Builder()
                .baseUrl(Constants.Urls.URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(AppClass.gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
    }

    @Singleton
    @Provides
    fun provideApiAuth(retrofit: Retrofit): ApiAuth
    {
        return retrofit.create(ApiAuth::class.java)
    }

    @Singleton
    @Provides
    fun provideApiFiles(retrofit: Retrofit): ApiFiles
    {
        return retrofit.create(ApiFiles::class.java)
    }

    @Singleton
    @Provides
    fun provideApiNews(retrofit: Retrofit): ApiNews
    {
        return retrofit.create(ApiNews::class.java)
    }

    @Singleton
    @Provides
    fun provideApiUser(retrofit: Retrofit): ApiUsers
    {
        return retrofit.create(ApiUsers::class.java)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService
    {
        return retrofit.create(ApiService::class.java)
    }
}
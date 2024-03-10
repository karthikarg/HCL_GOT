package com.hcl.got.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.hcl.got.GOTApplication
import com.hcl.got.data.api.GOTApiHelper
import com.hcl.got.data.api.GOTApiHelperImpl
import com.hcl.got.data.api.GOTApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GOTApplicationModule {

    val BASE_URL = "https://www.anapioficeandfire.com/api/"
    val cacheSize = (5 * 1024 * 1024).toLong()

    @Provides
    fun provideBaseUrl() =  BASE_URL


    @Provides
    @Singleton
    fun provideOkHttpClient() =  OkHttpClient
        .Builder()
        .cache(Cache(GOTApplication.applicationContext().cacheDir, cacheSize))
        .addInterceptor { chain ->

            var request = chain.request()
            request = if (hasNetwork(GOTApplication.applicationContext()))
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            chain.proceed(request)


        }
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GOTApiService = retrofit.create(GOTApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: GOTApiHelperImpl): GOTApiHelper = apiHelper

    fun hasNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
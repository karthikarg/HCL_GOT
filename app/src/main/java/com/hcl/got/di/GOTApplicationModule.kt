package com.hcl.got.di

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
            val originalResponse = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
            ) {
                // No cache headers, skip caching
                originalResponse
            } else {
                originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                    .build()
            }

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
}
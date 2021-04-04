package com.payback.pixiti.di

import android.content.Context
import com.payback.pixiti.data.PixabayApi
import com.payback.pixiti.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context) : OkHttpClient {
        val cacheSize = 10 * 1024 * 1024.toLong()
        val cache = Cache(context.cacheDir, cacheSize)

        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
                .addInterceptor(logger)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    @Provides
    @Singleton
    fun providePixabayApi(retrofit: Retrofit): PixabayApi =
            retrofit.create(PixabayApi::class.java)

}
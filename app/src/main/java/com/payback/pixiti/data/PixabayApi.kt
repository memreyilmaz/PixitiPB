package com.payback.pixiti.data

import com.payback.pixiti.BuildConfig
import com.payback.pixiti.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("?key=$KEY")
    suspend fun searchImages(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int
    ): ImageResponse

    companion object {
        const val KEY = BuildConfig.PIXABAY_API_KEY
    }
}
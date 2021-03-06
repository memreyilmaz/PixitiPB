package com.example.pixiti.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pixiti.paging.ImagePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixabayRepository @Inject constructor(private val pixabayApi: PixabayApi) {

    fun getImages(query: String) = Pager(
            config = PagingConfig(
                    pageSize = IMAGE_PAGE_SIZE,
                    maxSize = IMAGE_MAX_SIZE,
                    enablePlaceholders = false
            ), pagingSourceFactory = { ImagePagingSource(pixabayApi, query) }
    ).liveData

    companion object {
        private const val IMAGE_PAGE_SIZE = 20
        private const val IMAGE_MAX_SIZE = 100
    }
}
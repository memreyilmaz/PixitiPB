package com.payback.pixiti.paging

import androidx.paging.PagingSource
import com.payback.pixiti.data.PixabayApi
import com.payback.pixiti.model.Image
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(
        private val pixabayApi: PixabayApi,
        private val query: String
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = pixabayApi.searchImages(query, position, params.loadSize)
            val photos = response.images

            LoadResult.Page(
                    data = photos,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
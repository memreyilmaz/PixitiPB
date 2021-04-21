package com.example.pixiti.paging

import androidx.paging.PagingSource
import com.example.pixiti.data.PixabayApi
import com.example.pixiti.model.Image

class ImagePagingSource(
        private val pixabayApi: PixabayApi,
        private val query: String
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = pixabayApi.searchImages(query, position, params.loadSize)
            val images = response.images

            LoadResult.Page(
                    data = images,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = if (images.isEmpty() || position == LIMIT_PAGE_INDEX) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        /*
         * If search result contains less than 500 items Pixabay Api returns an empty list on last page
         * with HTTP 200 code and endOfPaginationReached param works properly but
         *
         * If search result contains more than 500 items and since Pixabay Api is limited to return
         * a maximum of 500 images per query, Pixabay api returns HTTP 400 with an empty message
         * and with response [text=[ERROR 400] "page" is out of valid range.] on last page
         * That is why we are obliged to check that if we are reached end of the response limit or
         * not with LIMIT_PAGE_INDEX parameter
        */
        private const val LIMIT_PAGE_INDEX = 26
        private const val STARTING_PAGE_INDEX = 1
    }
}
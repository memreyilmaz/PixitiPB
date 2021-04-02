package com.payback.pixiti.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.payback.pixiti.data.PixabayRepository
import com.payback.pixiti.model.Image
import com.payback.pixiti.utils.DEFAULT_QUERY
import kotlinx.coroutines.flow.Flow

class ImageViewModel @ViewModelInject constructor(private val repository: PixabayRepository) : ViewModel() {
    private var currentQuery: String = DEFAULT_QUERY
    private var currentSearchResult: Flow<PagingData<Image>>? = null

    fun searchImages(query: String): Flow<PagingData<Image>> {
        val lastResult = currentSearchResult
        if (query == currentQuery && lastResult != null) {
            return lastResult
        }
        currentQuery = query
        val newResult: Flow<PagingData<Image>> = repository.getImages(
                query = query
        )
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}
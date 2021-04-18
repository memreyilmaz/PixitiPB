package com.example.pixiti.ui.list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pixiti.data.PixabayRepository
import com.example.pixiti.utils.DEFAULT_QUERY

class ImageViewModel @ViewModelInject constructor(private val repository: PixabayRepository, @Assisted state: SavedStateHandle) : ViewModel() {
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val images = currentQuery.switchMap { queryString ->
        repository.getImages(queryString).cachedIn(viewModelScope)
    }

    fun searchImages(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
    }
}
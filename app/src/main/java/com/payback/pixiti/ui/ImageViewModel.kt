package com.payback.pixiti.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.payback.pixiti.data.PixabayRepository

class ImageViewModel @ViewModelInject constructor(private val repository: PixabayRepository) : ViewModel(){
}

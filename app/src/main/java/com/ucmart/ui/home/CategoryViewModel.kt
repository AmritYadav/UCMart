package com.ucmart.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucmart.data.category.repository.CategoryRepository
import com.ucmart.ui.home.model.Category
import com.ucmart.ui.home.model.CategoryListResult
import com.ucmart.utils.NetworkState
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel() {

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _categoryResult = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categoryResult

    fun loadCategories() = viewModelScope.launch {
        _networkState.value = NetworkState.LOADING
        val response = categoryRepository.categories()
        if(!response.categories.isNullOrEmpty()) {
            _categoryResult.value = response.categories
            _networkState.value = NetworkState.LOADED
        } else {
            _networkState.value = NetworkState.error(response.error)
        }
    }
}
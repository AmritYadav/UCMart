package com.ucmart.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucmart.data.product.repository.ProductRepository
import com.ucmart.ui.products.model.Product
import com.ucmart.utils.NetworkState
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _productResult = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _productResult

    fun loadProducts(categoryId: Int) = viewModelScope.launch {
        _networkState.value = NetworkState.LOADING
        val response = productRepository.products(categoryId)
        if(!response.products.isNullOrEmpty()) {
            _productResult.value = response.products
            _networkState.value = NetworkState.LOADED
        } else {
            _networkState.value = NetworkState.error(response.error)
        }
    }

}
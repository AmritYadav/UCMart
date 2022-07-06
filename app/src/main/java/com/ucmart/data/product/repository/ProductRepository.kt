package com.ucmart.data.product.repository

import com.ucmart.data.product.model.toProduct
import com.ucmart.ui.products.model.ProductListResult
import com.ucmart.utils.Result

class ProductRepository(private val remoteDataSource: ProductRemoteDataSource) {

    suspend fun products(categoryId: Int): ProductListResult {
        return when (val result = remoteDataSource.products(categoryId)) {
            is Result.Success -> ProductListResult(products = result.data.data.map { it.toProduct() })
            is Result.Error -> ProductListResult(error = result.exception.message)
        }
    }
}
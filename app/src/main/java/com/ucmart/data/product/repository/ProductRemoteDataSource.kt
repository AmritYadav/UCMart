package com.ucmart.data.product.repository

import com.ucmart.data.base.ApiResponse
import com.ucmart.data.product.api.ProductApi
import com.ucmart.data.product.model.ProductResponse
import com.ucmart.utils.PRODUCT_API
import com.ucmart.utils.Result
import com.ucmart.utils.safeApiCall
import java.io.IOException

class ProductRemoteDataSource(private val productApi: ProductApi) {

    suspend fun products(categoryId: Int): Result<ApiResponse<List<ProductResponse>>> = safeApiCall(
        call = { requestProducts(categoryId) },
        errorMessage = "Unable to load products list. Please your connection and try again"
    )

    private suspend fun requestProducts(categoryId: Int): Result<ApiResponse<List<ProductResponse>>> {
        val response = productApi.products(PRODUCT_API, prepareRequest(categoryId))
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(IOException(response.errorBody()?.charStream().toString()))
        }
    }

    private fun prepareRequest(categoryId: Int) =
        hashMapOf(
            "categoryId" to categoryId.toString(),
            "subCategoryID" to 0.toString()
        )
}
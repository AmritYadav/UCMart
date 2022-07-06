package com.ucmart.data.category.repository

import com.ucmart.data.base.ApiResponse
import com.ucmart.data.category.api.CategoryApi
import com.ucmart.data.category.model.CategoryResponse
import com.ucmart.utils.CATEGORY_API
import com.ucmart.utils.Result
import com.ucmart.utils.safeApiCall
import java.io.IOException

class CategoryRemoteDataSource(private val categoryApi: CategoryApi) {

    suspend fun categories() : Result<ApiResponse<List<CategoryResponse>>> = safeApiCall(
        call = {requestCategories()},
        errorMessage = "Unable to load category list. Please your connection and try again"
    )

    private suspend fun requestCategories() : Result<ApiResponse<List<CategoryResponse>>> {
        val response = categoryApi.categories(CATEGORY_API)
        val body = response.body()
        return if(response.isSuccessful && body != null) {
            Result.Success(body)
        } else {
            Result.Error(IOException(response.errorBody()?.charStream().toString()))
        }
    }
}
package com.ucmart.data.category.api

import com.ucmart.data.base.ApiResponse
import com.ucmart.data.category.model.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CategoryApi {

    @GET
    suspend fun categories(@Url url: String): Response<ApiResponse<List<CategoryResponse>>>
    
}
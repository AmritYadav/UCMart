package com.ucmart.data.product.api

import com.ucmart.data.base.ApiResponse
import com.ucmart.data.category.model.CategoryResponse
import com.ucmart.data.product.model.ProductResponse
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {

    @POST
    @FormUrlEncoded
    suspend fun products(@Url url: String, @FieldMap params: Map<String, String>): Response<ApiResponse<List<ProductResponse>>>

}
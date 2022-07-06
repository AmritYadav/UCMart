package com.ucmart.data.category.model

import com.squareup.moshi.Json
import com.ucmart.ui.home.model.Category
import retrofit2.http.Field

data class CategoryResponse(
    @field:Json(name = "categoryId") val id: String,
    @field:Json(name = "categoryName") val name: String,
    @field:Json(name = "categoryImg") val imgUrl: String,
    @field:Json(name = "categoryPosition") val categoryOrder: String,
    val isNew: String,
    val categoryStatus: String,
    val isAvailable: String,
    @field:Json(name = "subcategory") val subcategories: List<SubCategory>
)

fun CategoryResponse.toCategory() = Category(
    id = id.toInt(),
    name = name,
    imgUrl = imgUrl,
    categoryOrder = categoryOrder.toInt(),
    isNew = isNew == "1",
    isAvailable = isAvailable == "1"
)
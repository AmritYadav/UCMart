package com.ucmart.data.product.model

import com.squareup.moshi.Json
import com.ucmart.ui.products.model.Product

data class ProductResponse(
    val productId: Int,
    val categoryId: Int,
    val subCategoryId: Int,
    @field:Json(name = "productName") val name: String,
    @field:Json(name = "productImg") val imgUrl: String,
    val unit: String,
    val mrp: String,
    val sellingPrice: String,
    val isAvailable: String
)

fun ProductResponse.toProduct() = Product(
    productId,
    categoryId,
    name,
    imgUrl,
    unit,
    sellingPrice.toInt(),
    mrp.toInt()
)
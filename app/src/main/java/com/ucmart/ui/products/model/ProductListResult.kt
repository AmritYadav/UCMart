package com.ucmart.ui.products.model

data class ProductListResult(
    val products: List<Product>? = null,
    val error: String? = null
)
package com.ucmart.ui.home.model

data class CategoryListResult(
    val categories: List<Category>? = null,
    val error: String? = null
)
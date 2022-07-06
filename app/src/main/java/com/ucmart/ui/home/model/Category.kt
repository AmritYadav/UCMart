package com.ucmart.ui.home.model

data class Category(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val categoryOrder: Int,
    val isNew: Boolean,
    val isAvailable: Boolean
)
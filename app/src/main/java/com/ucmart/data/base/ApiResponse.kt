package com.ucmart.data.base

data class ApiResponse<T>(
    val status: Int,
    val msg: String,
    val data: T
)
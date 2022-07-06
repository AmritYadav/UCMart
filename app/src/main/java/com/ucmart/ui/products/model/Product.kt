package com.ucmart.ui.products.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val productId: Int,
    val categoryId: Int,
    val name: String,
    val imgUrl: String,
    val unit: String,
    val pricePerUnit: Int,
    val mrp: Int
) : Parcelable
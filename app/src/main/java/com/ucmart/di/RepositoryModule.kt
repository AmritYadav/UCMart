package com.ucmart.di

import android.content.Context
import android.content.SharedPreferences
import com.ucmart.data.category.repository.CategoryRemoteDataSource
import com.ucmart.data.category.repository.CategoryRepository
import com.ucmart.data.product.repository.ProductRemoteDataSource
import com.ucmart.data.product.repository.ProductRepository
import org.koin.core.module.Module
import org.koin.dsl.module

private const val UCMART_USER_PREFS = "UCMART_USER_PREFS"

private val categoryRepository = module {
    factory { CategoryRemoteDataSource(categoryApi = get()) }
    factory { CategoryRepository(remoteDataSource = get()) }
}

private val productRepository = module {
    factory { ProductRemoteDataSource(productApi = get()) }
    factory { ProductRepository(remoteDataSource = get()) }
}

val repoModules: List<Module> = listOf(
    categoryRepository,
    productRepository
)

private fun provideSharedPreference(context: Context): SharedPreferences {
    return context.getSharedPreferences(UCMART_USER_PREFS, Context.MODE_PRIVATE)
}
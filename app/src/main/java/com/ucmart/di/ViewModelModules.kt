package com.ucmart.di

import com.ucmart.ui.MainHostViewModel
import com.ucmart.ui.home.CategoryViewModel
import com.ucmart.ui.products.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModels = module {
    viewModel { MainHostViewModel() }
    viewModel { CategoryViewModel(categoryRepository = get()) }
    viewModel { ProductViewModel(productRepository = get()) }
}
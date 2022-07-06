package com.ucmart.data.category.repository

import com.ucmart.data.category.model.toCategory
import com.ucmart.ui.home.model.CategoryListResult
import com.ucmart.utils.Result

class CategoryRepository(private val remoteDataSource: CategoryRemoteDataSource) {

    suspend fun categories(): CategoryListResult {
        return when (val result = remoteDataSource.categories()) {
            is Result.Success -> CategoryListResult(categories = result.data.data.map { it.toCategory() })
            is Result.Error -> CategoryListResult(error = result.exception.message)
        }
    }

}
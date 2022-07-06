package com.ucmart.ui.mainnew.home.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ucmart.ui.home.model.Category

class CategoryAdapter(
    private val callback: (categoryId: Int, name: String) -> Unit
) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder.create(parent)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position), callback)

}

private class CategoryDiffUtil: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
        oldItem.id == newItem.id
}
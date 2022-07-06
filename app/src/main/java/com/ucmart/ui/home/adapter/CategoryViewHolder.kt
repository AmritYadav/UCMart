package com.ucmart.ui.mainnew.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ucmart.R
import com.ucmart.databinding.ItemCategoryBinding
import com.ucmart.ui.home.model.Category
import com.ucmart.utils.loadOriginalImage

class CategoryViewHolder(private val binding: ItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category, callback: (categoryId: Int, name: String) -> Unit) {
        binding.image.loadOriginalImage(category.imgUrl)
        binding.name.text = category.name
        itemView.setOnClickListener { callback.invoke(category.id, category.name) }
    }

    companion object {
        fun create(parent: ViewGroup) =
            CategoryViewHolder(
                ItemCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

}
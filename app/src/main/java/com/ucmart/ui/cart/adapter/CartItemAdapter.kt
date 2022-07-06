package com.ucmart.ui.mainnew.cart.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ucmart.ui.cart.adapter.CartItemViewHolder
import com.ucmart.ui.cart.model.CartItem

class CartItemAdapter(private val callback: (isIncrement: Boolean, pos: Int) -> Unit) :
    ListAdapter<CartItem, CartItemViewHolder>(CartItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) =
        holder.bind(getItem(position), callback)

}

class CartItemDiffUtil : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.productId == newItem.productId
    }
}
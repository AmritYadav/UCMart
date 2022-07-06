package com.ucmart.ui.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ucmart.R
import com.ucmart.databinding.ItemCartBinding
import com.ucmart.ui.cart.model.CartItem
import com.ucmart.utils.loadOriginalImage

class CartItemViewHolder(private val binding: ItemCartBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(cartItem: CartItem, callback: (isIncrement: Boolean, pos: Int) -> Unit) {
        setupUI(cartItem)
        binding.increment.setOnClickListener { callback.invoke(true, adapterPosition) }
        binding.decrement.setOnClickListener { callback.invoke(false, adapterPosition) }
    }

    private fun setupUI(cartItem: CartItem) {
        binding.productImage.loadOriginalImage(cartItem.imgUrl)
        binding.productName.text = cartItem.name
        binding.price.text = itemView.context.getString(
            R.string.product_amount_unit,
            cartItem.pricePerUnit.toString(),
            cartItem.unit
        )
        binding.quantity.setText(cartItem.quantity.toString())
    }

    companion object {
        fun create(parent: ViewGroup) =
            CartItemViewHolder(
                ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }
}
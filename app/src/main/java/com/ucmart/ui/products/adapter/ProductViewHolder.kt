package com.ucmart.ui.products.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ucmart.R
import com.ucmart.databinding.ItemProductBinding
import com.ucmart.ui.products.model.Product
import com.ucmart.utils.loadOriginalImage
import com.ucmart.utils.visibleGone

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product, callback: (product: Product)-> Unit) {
        binding.productImage.loadOriginalImage(product.imgUrl.trim())
        binding.productName.text = product.name.trim()

        binding.mrpPrice.text = itemView.context.getString(R.string.product_mrp_amount, product.mrp.toString())
        binding.mrpPrice.paintFlags = binding.mrpPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        binding.discountedPrice.text = itemView.context.getString(R.string.product_amount, product.pricePerUnit.toString())
        binding.unit.text = product.unit.trim()

        binding.mrpPrice.visibleGone(product.mrp > 0)

        binding.addToCart.setOnClickListener { callback.invoke(product) }
    }

    companion object {
        fun create(parent: ViewGroup): ProductViewHolder = ProductViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}
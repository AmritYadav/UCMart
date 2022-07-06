package com.ucmart.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ucmart.R
import com.ucmart.databinding.FragmentAddToCartDialogBinding
import com.ucmart.ui.MainHostViewModel
import com.ucmart.ui.cart.model.CartItem
import com.ucmart.ui.products.model.Product
import com.ucmart.utils.afterTextChanged
import com.ucmart.utils.loadOriginalImage
import com.ucmart.utils.showToast

class AddToCartDialogFragment : BottomSheetDialogFragment() {

    private val mainHostViewModel by activityViewModels<MainHostViewModel>()

    private val args by navArgs<AddToCartDialogFragmentArgs>()

    private lateinit var binding: FragmentAddToCartDialogBinding

    private var itemQuantity: Int = 1

    private lateinit var cartItem: CartItem

    private lateinit var cartItemMap: HashMap<Int, CartItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddToCartDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartItemMap = mainHostViewModel.cartItems.value ?: hashMapOf()

        initCartItem()
        initUi()

        binding.quantity.setOnClickListener {
            val v = it as EditText
            v.isCursorVisible = true
        }
        binding.quantity.afterTextChanged {
            itemQuantity = if (it.isEmpty()) 0 else it.toInt()
            binding.addToCart.isEnabled = itemQuantity > 0
        }
        binding.decrement.setOnClickListener {
            handleQuantityUpdate(isIncrement = false)
        }
        binding.increment.setOnClickListener {
            handleQuantityUpdate(isIncrement = true)
        }
        binding.addToCart.setOnClickListener {
            cartItem.quantity = itemQuantity
            cartItemMap[args.product.productId] = cartItem
            mainHostViewModel.cartItems.value = cartItemMap
            activity?.showToast("Item(s) added to your cart.")
            dialog?.dismiss()
        }
    }

    private fun initUi() {
        with(args.product) {
            binding.productImage.loadOriginalImage(imgUrl)
            binding.name.text = name
            binding.price.text = getString(R.string.product_amount, pricePerUnit.toString())
            binding.perKg.text = getString(R.string.prompt_per_kg, unit.toString())
        }

        binding.quantity.setText(itemQuantity.toString())
        binding.quantity.isCursorVisible = false
    }

    private fun initCartItem() {
        with(args.product) {
            cartItem = cartItemMap[productId] ?: CartItem(
                productId, name, imgUrl, unit, pricePerUnit, itemQuantity
            )
        }
        // set existing item quantity if already present in the cart
        itemQuantity = cartItem.quantity
    }

    private fun handleQuantityUpdate(isIncrement: Boolean) {
        binding.quantity.isCursorVisible = false
        if (isIncrement && itemQuantity == 999) return
        if (!isIncrement && itemQuantity == 0) {
            binding.addToCart.isEnabled = false
            return
        }
        binding.addToCart.isEnabled = true
        if (isIncrement) itemQuantity++ else itemQuantity--
        binding.quantity.setText(itemQuantity.toString())
    }
}
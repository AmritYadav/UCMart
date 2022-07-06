package com.ucmart.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ucmart.R
import com.ucmart.databinding.FragmentCartBinding
import com.ucmart.ui.MainHostViewModel
import com.ucmart.ui.cart.model.CartItem
import com.ucmart.ui.mainnew.cart.adapters.CartItemAdapter

class CartFragment : Fragment() {

    private val hostActivityViewModel by activityViewModels<MainHostViewModel>()

    private lateinit var binding: FragmentCartBinding

    private lateinit var cartItemAdapter: CartItemAdapter
    private lateinit var cartItems: ArrayList<CartItem>
    private lateinit var cartItemMap: HashMap<Int, CartItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.labelDeliveryChargeApplicable.text = HtmlCompat.fromHtml(
            getString(R.string.prompt_delivery_charge),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        setupRecycler()
        setupObservers()
        binding.continueToPlaceOrder.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_cart_to_nav_login))
    }

    private fun setupObservers() {
        hostActivityViewModel.cartItems.observe(viewLifecycleOwner) {
            cartItemMap = it
            cartItems = convertMapToListCartItem(cartItemMap)
            cartItemAdapter.submitList(cartItems)
            calculateTotalAmount(cartItems)
        }
    }

    private fun setupRecycler() {
        cartItemAdapter = CartItemAdapter { isIncrement, pos ->
            handleCartItemQuantityChange(isIncrement, pos)
        }
        binding.cartItemRecycler.apply {
            adapter = cartItemAdapter
            setHasFixedSize(true)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

    private fun convertMapToListCartItem(cartItemMap: HashMap<Int, CartItem>) =
        ArrayList<CartItem>(cartItemMap.values)

    private fun calculateTotalAmount(cartItems: List<CartItem>) {
        var amount = 0
        cartItems.forEach { cartItem ->
            amount += cartItem.quantity * cartItem.pricePerUnit
        }
        val deliveryCharge = if (amount < 100) 20 else 0
        val totalAmount = amount + deliveryCharge

        binding.totalCartAmount.text = getString(R.string.product_amount, amount.toString())
        binding.deliveryCharge.text =
            getString(R.string.placeholder_delivery_charge, deliveryCharge.toString())
        binding.totalAmount.text = getString(R.string.product_amount, totalAmount.toString())
    }

    private fun handleCartItemQuantityChange(isIncrement: Boolean, pos: Int) {
        val cartItem = cartItems[pos]
        if (!isIncrement && cartItem.quantity == 1) {
            cartItems.remove(cartItem)
            cartItemMap.remove(cartItem.productId)
            hostActivityViewModel.cartItems.value = cartItemMap
            cartItemAdapter.notifyItemRemoved(pos)
            binding.continueToPlaceOrder.isEnabled = cartItems.isNotEmpty()
            return
        }
        if (isIncrement && cartItem.quantity == 999) return
        if (isIncrement) cartItem.quantity++ else cartItem.quantity--
        cartItemAdapter.notifyItemChanged(pos)
        calculateTotalAmount(cartItems)
    }
}

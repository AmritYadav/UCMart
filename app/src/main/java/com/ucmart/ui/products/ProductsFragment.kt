package com.ucmart.ui.products

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ucmart.R
import com.ucmart.databinding.FragmentProductsBinding
import com.ucmart.ui.MainHostViewModel
import com.ucmart.ui.products.adapter.ProductAdapter
import com.ucmart.utils.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding

    private val mainHostViewModel by activityViewModels<MainHostViewModel>()
    private val productViewModel by viewModel<ProductViewModel>()

    private lateinit var productAdapter: ProductAdapter

    private val args by navArgs<ProductsFragmentArgs>()

    private var cartBadge: TextView? = null
    private var cartItemCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.toolbar?.title = args.categoryName

        setupObserver()
        setupRecycler()

        productViewModel.loadProducts(args.categoryId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        menu.findItem(R.id.action_cart)?.let { menuItem ->
            val actionView = menuItem.actionView
            cartBadge = actionView.findViewById(R.id.cart_badge)
            cartBadge?.visibleGone(cartItemCount > 0)
            actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cart && cartItemCount > 0) {
            findNavController().navigate(R.id.action_nav_products_to_nav_cart)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        activity?.toolbar?.title = args.categoryName
    }

    private fun setupObserver() {
        mainHostViewModel.cartItems.observe(viewLifecycleOwner) {
            cartItemCount = it.count()
            cartBadge?.visibleGone(cartItemCount > 0)
        }
        productViewModel.networkState.observe(viewLifecycleOwner) {
            when(it) {
                NetworkState.LOADING -> binding.progress.visible(activity?.window)
                else -> {
                    binding.progress.gone(activity?.window)
                    if(!it.msg.isNullOrEmpty()) updateUser(it.msg)
                }
            }
        }
        productViewModel.products.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }
    }

    private fun setupRecycler() {
        productAdapter = ProductAdapter {product ->
            val action = ProductsFragmentDirections.actionNavProductsToNavAddToCart(product)
            findNavController().navigate(action)
        }
        binding.rvProducts.adapter = productAdapter
    }

    private fun updateUser(msg: String) {
        context?.showToast(msg)
    }
}
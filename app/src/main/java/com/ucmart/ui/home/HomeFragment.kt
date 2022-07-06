package com.ucmart.ui.home

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.ucmart.R
import com.ucmart.databinding.FragmentHomeBinding
import com.ucmart.ui.MainHostViewModel
import com.ucmart.ui.mainnew.home.adapters.CategoryAdapter
import com.ucmart.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val mainHostViewModel by activityViewModels<MainHostViewModel>()
    private val categoryViewModel by viewModel<CategoryViewModel>()

    private lateinit var categoryAdapter: CategoryAdapter

    private var cartBadge: TextView? = null
    private var cartItemCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupRecycler()

        categoryViewModel.loadCategories()
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
            findNavController().navigate(R.id.action_nav_home_to_cartFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObserver() {
        mainHostViewModel.cartItems.observe(viewLifecycleOwner) {
            cartItemCount = it.count()
            cartBadge?.visibleGone(cartItemCount > 0)
        }
        categoryViewModel.networkState.observe(viewLifecycleOwner) {
            when(it) {
                NetworkState.LOADING -> binding.progress.visible(activity?.window)
                else -> {
                    binding.progress.gone(activity?.window)
                    if(!it.msg.isNullOrEmpty()) updateUser(it.msg)
                }
            }
        }
        categoryViewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }
    }

    private fun setupRecycler() {
        categoryAdapter = CategoryAdapter {categoryId, name ->
            val action = HomeFragmentDirections.actionNavHomeToNavProducts(categoryId, name)
            findNavController().navigate(action)
        }
        binding.rvCategories.adapter = categoryAdapter
    }

    private fun updateUser(msg: String) {
        context?.showToast(msg)
    }
}

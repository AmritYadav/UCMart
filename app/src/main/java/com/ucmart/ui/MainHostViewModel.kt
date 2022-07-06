package com.ucmart.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ucmart.ui.cart.model.CartItem

class MainHostViewModel : ViewModel() {

    var cartItems = MutableLiveData<HashMap<Int, CartItem>>()

}
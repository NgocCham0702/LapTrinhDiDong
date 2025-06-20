package com.example.goiapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.getProduct()
                _product.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
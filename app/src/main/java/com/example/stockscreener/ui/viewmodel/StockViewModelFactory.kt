package com.example.stockscreener.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stockscreener.data.repository.StockRepository

class StockViewModelFactory(
    private val repository: StockRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StockViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.example.stockscreener.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stockscreener.data.model.Stock
import com.example.stockscreener.data.repository.StockRepository


class StockViewModel (private val repository: StockRepository) : ViewModel(){

    private val _stocks = MutableLiveData<List<Stock>>()
    val stocks: LiveData<List<Stock>> = _stocks

    private val _loading = MutableLiveData<Boolean>()
        val loading: LiveData<Boolean> = _loading

    init {
        loadStocks()
    }
    fun loadStocks() {
        _loading.value=true
        try {
            _stocks.value= repository.getStocks()
        } finally {
            _loading.value= false
        }
    }

    fun toggleFavorite( stockId: Int){
        _stocks.value= repository.toggleFavorite(stockId)
    }

    fun searchStocks(query: String) {
        _stocks.value= if (query.isBlank()){
            repository.getStocks()
        } else {
            repository.searchStocks(query)
        }
    }
}
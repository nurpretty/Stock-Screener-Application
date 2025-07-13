package com.example.stockscreener.data.repository

import android.app.DownloadManager.Query
import com.example.stockscreener.data.local.StockDataSource
import com.example.stockscreener.data.model.Stock


class StockRepository (private val stockDataSource: StockDataSource) {

    private var cachedStocks: List<Stock> = emptyList()
    private val favoriteStocks = mutableSetOf<Int>()

    fun getStocks() : List<Stock> {
        if (cachedStocks.isEmpty()){
            cachedStocks = stockDataSource.getStocksFromAssets().map { stock ->
                stock.copy(isFavorite = favoriteStocks.contains(stock.id))
            }
        }
        return cachedStocks
    }

    fun toggleFavorite(stockId: Int) : List <Stock>{
        if (favoriteStocks.contains(stockId)){
            favoriteStocks.remove(stockId)
        } else {
            favoriteStocks.add(stockId)
        }

        cachedStocks= cachedStocks.map { stock ->
            if (stock.id == stockId) {
                stock.copy(isFavorite = !stock.isFavorite)
            } else {
                stock
            }
        }
        return cachedStocks
    }

    fun searchStocks(query: String) : List<Stock> {
        return cachedStocks.filter { stock ->
            stock.name.contains(query, ignoreCase = true) ||
                    stock.symbol.contains(query, ignoreCase = true)
        }
    }


}
package com.example.stockscreener.data.local

import android.content.Context
import com.example.stockscreener.data.model.Stock
import com.google.gson.Gson

import java.io.IOException

class StockDataSource(private val context: Context) {

    data class StockResponse(val stocks: List<Stock>)

    fun getStocksFromAssets(): List<Stock> {
        return try {
            val jsonString = context.assets.open("stocks.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val stockResponse = gson.fromJson(jsonString, StockResponse::class.java)
            stockResponse.stocks
        } catch (e: IOException) {
            emptyList()
        }
    }
}
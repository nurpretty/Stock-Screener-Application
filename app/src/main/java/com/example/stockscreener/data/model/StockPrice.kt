package com.example.stockscreener.data.model

import com.google.gson.annotations.SerializedName

data class StockPrice(
    @SerializedName("current_price")
    val currentPrice: Price,
    @SerializedName("price_change")
    val priceChange: Double,
    @SerializedName("percentage_change")
    val percentageChange: Double
)

data class Price(
    val amount: String,
    val currency: String
)
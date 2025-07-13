package com.example.stockscreener.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.stockscreener.R
import com.example.stockscreener.data.model.Stock

class StockAdapter(
    private var stocks: List<Stock>,
    private val onFavoriteClick: (Stock) -> Unit
) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    class StockViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logoImageView: ImageView = view.findViewById(R.id.ivLogo)
        val symbolTextView: TextView = view.findViewById(R.id.tvSymbol)
        val nameTextView: TextView = view.findViewById(R.id.tvName)
        val priceTextView: TextView = view.findViewById(R.id.tvPrice)
        val changeTextView: TextView = view.findViewById(R.id.tvChange)
        val favoriteImageView: ImageView = view.findViewById(R.id.ivFavorite)
        val trendImageView: ImageView = view.findViewById(R.id.ivTrend)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stocks[position]
        val context = holder.itemView.context

        // Set text data
        holder.symbolTextView.text = stock.symbol
        holder.nameTextView.text = stock.name
        holder.priceTextView.text = "$${stock.stockPrice.currentPrice.amount}"

        // Format change text
        val change = stock.stockPrice.priceChange
        val percentChange = stock.stockPrice.percentageChange
        holder.changeTextView.text = String.format("%.2f (%.2f%%)", change, percentChange)


        when {
            change > 0 -> {
                // Positive change
                holder.changeTextView.setTextColor(ContextCompat.getColor(context, R.color.stock_positive))
                holder.priceTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.stock_positive_background))
                holder.trendImageView.setImageResource(R.drawable.ic_trending_up)
                holder.trendImageView.setColorFilter(ContextCompat.getColor(context, R.color.stock_positive))
                Log.d("StockAdapter", "${stock.symbol}: UP trend")
            }
            change < 0 -> {
                // Negative change
                holder.changeTextView.setTextColor(ContextCompat.getColor(context, R.color.stock_negative))
                holder.priceTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.stock_negative_background))
                holder.trendImageView.setImageResource(R.drawable.ic_trending_down)
                holder.trendImageView.setColorFilter(ContextCompat.getColor(context, R.color.stock_negative))
                Log.d("StockAdapter", "${stock.symbol}: DOWN trend")
            }
            else -> {

                holder.changeTextView.setTextColor(ContextCompat.getColor(context, R.color.stock_neutral))
                holder.priceTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.md_theme_light_surfaceVariant))
                holder.trendImageView.setImageResource(R.drawable.ic_trending_flat)
                holder.trendImageView.setColorFilter(ContextCompat.getColor(context, R.color.stock_neutral))
                Log.d("StockAdapter", "${stock.symbol}: FLAT trend")
            }
        }

        // Load logo
        if (!stock.logoUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(stock.logoUrl)
                .placeholder(R.drawable.ic_favorite)
                .error(R.drawable.ic_favorite)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.logoImageView)
        } else {
            holder.logoImageView.setImageResource(R.drawable.ic_favorite)
        }

        // Set favorite icon
        holder.favoriteImageView.setImageResource(
            if (stock.isFavorite) R.drawable.ic_favorite_filles else R.drawable.ic_favorite
        )

        holder.favoriteImageView.setOnClickListener {
            onFavoriteClick(stock)
        }
    }

    override fun getItemCount() = stocks.size

    fun updateStocks(newStocks: List<Stock>) {
        Log.d("StockAdapter", "Updating stocks: ${newStocks.size} items")
        stocks = newStocks
        notifyDataSetChanged()
    }
}
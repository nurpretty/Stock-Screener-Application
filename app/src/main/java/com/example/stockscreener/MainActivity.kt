package com.example.stockscreener

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stockscreener.data.local.StockDataSource
import com.example.stockscreener.data.repository.StockRepository
import com.example.stockscreener.ui.adapter.StockAdapter
import com.example.stockscreener.ui.viewmodel.StockViewModel
import com.example.stockscreener.ui.viewmodel.StockViewModelFactory
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StockViewModel
    private lateinit var stockAdapter: StockAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var refreshButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupViewModel()
        setupRecyclerView()
        setupSearchView()
        observeData()
    }

    private fun setupViews() {
        recyclerView = findViewById(R.id.rvStocks)
        searchEditText = findViewById(R.id.etSearch)
        progressBar = findViewById(R.id.progressBar)
        refreshButton = findViewById(R.id.btnRefresh)

        refreshButton.setOnClickListener {
            viewModel.loadStocks()
        }
    }

    private fun setupViewModel() {
        val stockDataSource = StockDataSource(this)
        val repository = StockRepository(stockDataSource)
        val viewModelFactory = StockViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[StockViewModel::class.java]
    }

    private fun setupRecyclerView() {
        stockAdapter = StockAdapter(emptyList()) { stock ->
            viewModel.toggleFavorite(stock.id)
        }
        recyclerView.apply {
            adapter = stockAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupSearchView() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchStocks(s.toString())
            }
        })
    }

    private fun observeData() {
        viewModel.stocks.observe(this) { stocks ->
            stockAdapter.updateStocks(stocks)
        }

        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
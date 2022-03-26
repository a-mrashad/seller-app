package com.mazaj.seller.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityMainBinding
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.ui.login.view.LoginActivity
import com.mazaj.seller.ui.main.viewModel.MainViewModel
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.shared.network.OnFetchingData

class MainActivity : BaseActivity(), OnFetchingData {
    override val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val onRequestClicked: (String) -> (Unit) = { startActivity(Intent(this, OrderDetailsActivity::class.java).apply { putExtra("id", it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupOnFetchingData()
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.logout.setOnClickListener { viewModel.onLogoutClicked() }
    }

    private fun setObservers() {
        viewModel.onLogoutSucceeded.observe(this, Observer { startActivity(Intent(this, LoginActivity::class.java).newTask()) })
        viewModel.newOrdersLiveData.observe(this, Observer {
            binding.tvNewCounter.text = it.size.toString()
            binding.tvNoNewOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvNewOrders.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvNewOrders.adapter = NewOrdersAdapter(it.toMutableList(), onRequestClicked)
        })
        viewModel.acceptedOrdersLiveData.observe(this, Observer {
            binding.tvAcceptedCounter.text = it.size.toString()
            binding.tvNoAcceptedOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvAcceptedOrders.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvAcceptedOrders.adapter = RespondedOrdersAdapter(it.toMutableList(), onRequestClicked)
        })
        viewModel.readyOrdersLiveData.observe(this, Observer {
            binding.tvReadyCounter.text = it.size.toString()
            binding.tvNoReadyOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvReadyOrders.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvAcceptedOrders.adapter = RespondedOrdersAdapter(it.toMutableList(), onRequestClicked)
        })
    }
}

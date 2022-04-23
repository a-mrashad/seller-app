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
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.ACCEPTED
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.ACCEPTED_STATUS
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.NEW
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.NEW_STATUS
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.READY
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.READY_STATUS
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.ordersList.view.OrdersListActivity
import com.mazaj.seller.ui.ordersList.view.OrdersListActivity.Companion.STATUS_KEY
import com.mazaj.seller.ui.shared.network.OnFetchingData

class MainActivity : BaseActivity(), OnFetchingData {
    override val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val onRequestClicked: (Long, String) -> (Unit) = { value, key -> startActivity(Intent(this, OrderDetailsActivity::class.java).apply {
        putExtra(key, value) })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupOnFetchingData()
        setListeners()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrders()
    }

    private fun setListeners() {
        binding.logout.setOnClickListener { viewModel.onLogoutClicked() }
        binding.tvNewCounter.setOnClickListener { openOrdersList(NEW_STATUS) }
        binding.tvAcceptedCounter.setOnClickListener { openOrdersList(ACCEPTED_STATUS) }
        binding.tvReadyCounter.setOnClickListener { openOrdersList(READY_STATUS) }
        binding.pullToRefresh.setOnRefreshListener { viewModel.getOrders() }
    }

    private fun openOrdersList(status: Int) {
        startActivity(Intent(this, OrdersListActivity::class.java).apply { putExtra(STATUS_KEY, status) })
    }

    private fun setObservers() {
        viewModel.onLogoutSucceeded.observe(this, Observer { startActivity(Intent(this, LoginActivity::class.java).newTask()) })
        viewModel.newOrdersLiveData.observe(this, Observer {
            binding.tvNoNewOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvNewOrders.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvNewOrders.adapter = NewOrdersAdapter(it.toMutableList(), onRequestClicked)
            binding.pullToRefresh.isRefreshing = false
        })
        viewModel.acceptedOrdersLiveData.observe(this, Observer {
            binding.tvNoAcceptedOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvAcceptedOrders.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvAcceptedOrders.adapter = RespondedOrdersAdapter(it.toMutableList(), onRequestClicked)
            binding.pullToRefresh.isRefreshing = false
        })
        viewModel.readyOrdersLiveData.observe(this, Observer {
            binding.tvNoReadyOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvReadyOrders.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            binding.rvAcceptedOrders.adapter = RespondedOrdersAdapter(it.toMutableList(), onRequestClicked)
            binding.pullToRefresh.isRefreshing = false
        })
        viewModel.overviewCountsLiveData.observe(this, Observer { counts ->
            val zeroCount = "0"
            binding.tvNewCounter.text = counts?.filter { it.status == NEW }?.getOrNull(0)?.total?.toString() ?: zeroCount
            binding.tvAcceptedCounter.text = counts?.filter { it.status == ACCEPTED }?.getOrNull(0)?.total?.toString() ?: zeroCount
            binding.tvReadyCounter.text = counts?.filter { it.status == READY }?.getOrNull(0)?.total?.toString() ?: zeroCount
            binding.pullToRefresh.isRefreshing = false
        })
    }
}

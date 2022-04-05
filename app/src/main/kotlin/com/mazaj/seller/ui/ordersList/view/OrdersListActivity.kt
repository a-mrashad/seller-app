package com.mazaj.seller.ui.ordersList.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityOrdersListBinding
import com.mazaj.seller.ui.main.view.RespondedOrdersAdapter
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.NEW_STATUS
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.READY_STATUS
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.ordersList.viewModel.OrdersListViewModel
import com.mazaj.seller.ui.shared.network.OnFetchingData

class OrdersListActivity : BaseActivity(), OnFetchingData {
    override val viewModel by lazy { ViewModelProvider(this)[OrdersListViewModel::class.java] }
    private val binding by lazy { ActivityOrdersListBinding.inflate(layoutInflater) }
    private val onRequestClicked: (String) -> (Unit) =
        { startActivity(Intent(this, OrderDetailsActivity::class.java).apply { putExtra("id", it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupOnFetchingData()
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        viewModel.onStatusReceived(intent.extras?.getInt(STATUS_KEY, 0) ?: 0)
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.icBack.setOnClickListener { onBackPressed() }
    }

    private fun setObservers() {
        viewModel.ordersLiveData.observe(this, Observer {
            binding.rvItems.adapter = RespondedOrdersAdapter(it.toMutableList(), onRequestClicked, isFullScreen = true)
            binding.tvNoItemsFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.tvTitle.text = when (intent.extras?.getInt(STATUS_KEY, 0) ?: 0) {
                NEW_STATUS -> getString(R.string.new_label)
                READY_STATUS -> getString(R.string.ready_label)
                else -> getString(R.string.accepted_label)
            }
        })
    }

    companion object { const val STATUS_KEY = "status_key" }
}

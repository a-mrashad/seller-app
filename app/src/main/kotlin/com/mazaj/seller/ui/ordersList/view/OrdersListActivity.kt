package com.mazaj.seller.ui.ordersList.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityOrdersListBinding
import com.mazaj.seller.extensions.getRequiredIntent
import com.mazaj.seller.ui.main.view.RespondedOrdersAdapter
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.NEW_STATUS
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.READY_STATUS
import com.mazaj.seller.ui.ordersList.viewModel.OrdersListViewModel
import com.mazaj.seller.ui.shared.pagination.PaginationView

class OrdersListActivity : BaseActivity(), PaginationView {
    override val viewModel by lazy { ViewModelProvider(this)[OrdersListViewModel::class.java] }
    private val binding by lazy { ActivityOrdersListBinding.inflate(layoutInflater) }
    override val recyclerView by lazy { binding.rvItems }
    private val onRequestClicked: (Long, String) -> (Unit) = { value, key -> startActivity(this.getRequiredIntent(key, value)) }
    private val ordersAdapter = RespondedOrdersAdapter(mutableListOf(), onRequestClicked, isFullScreen = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.onStatusReceived(intent.extras?.getInt(STATUS_KEY, 0) ?: 0)
        setupPagination()
        setListeners()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFirstPage()
    }

    private fun setupPagination() {
        setupRecyclerViewPagination()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrdersListActivity)
            adapter = ordersAdapter
        }
    }

    private fun setListeners() {
        binding.icBack.setOnClickListener { onBackPressed() }
        binding.pullToRefresh.setOnRefreshListener { viewModel.loadFirstPage() }
    }

    private fun setObservers() {
        viewModel.statusLiveData.observe(this) {
            binding.tvListTitle.text = when (it) {
                NEW_STATUS -> getString(R.string.new_label)
                READY_STATUS -> getString(R.string.ready_label)
                else -> getString(R.string.accepted_label)
            }
        }
        viewModel.itemList.observe(this) {
            if (it.isEmpty()) {
                binding.tvNoItemsFound.visibility = View.VISIBLE
                ordersAdapter.updateList(it)
                recyclerView.visibility = View.GONE
                binding.tvCount.text = "(0)"
            } else {
                binding.tvNoItemsFound.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                ordersAdapter.updateList(it)
            }
            binding.pullToRefresh.isRefreshing = false
        }
        viewModel.isListLoading.observe(this) {
            binding.loadingBar.loadingBar.visibility = if (it) View.VISIBLE else View.GONE
        }
        viewModel.countLiveData.observe(this) {
            binding.tvCount.text = "($it)"
        }
    }

    override fun onNotificationStarted() {
        binding.customNotification.customNotification.visibility = View.VISIBLE
        binding.layout.visibility = View.GONE
    }

    override fun onNotificationEnded() {
        binding.customNotification.customNotification.visibility = View.GONE
        binding.layout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (binding.layout.visibility == View.VISIBLE) super.onBackPressed()
    }

    companion object { const val STATUS_KEY = "status_key" }
}

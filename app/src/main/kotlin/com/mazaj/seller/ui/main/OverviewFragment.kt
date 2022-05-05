package com.mazaj.seller.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseFragment
import com.mazaj.seller.databinding.FragmentOverviewBinding
import com.mazaj.seller.extensions.newTask
import com.mazaj.seller.ui.login.view.LoginActivity
import com.mazaj.seller.ui.main.view.NewOrdersAdapter
import com.mazaj.seller.ui.main.view.RespondedOrdersAdapter
import com.mazaj.seller.ui.main.viewModel.MainViewModel
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.ordersList.view.OrdersListActivity
import com.mazaj.seller.ui.shared.network.OnFetchingData

class OverviewFragment : BaseFragment(), OnFetchingData {
    override val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val binding by lazy { FragmentOverviewBinding.inflate(layoutInflater) }
    private val onRequestClicked: (Long, String) -> (Unit) = { value, key -> startActivity(Intent(requireContext(), OrderDetailsActivity::class.java).apply {
        putExtra(key, value) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onStart() {
        super.onStart()
        setupOnFetchingData()
        setListeners()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getOrders()
    }

    private fun setListeners() {
        binding.tvNewCounter.setOnClickListener { openOrdersList(MainViewModel.NEW_STATUS) }
        binding.tvAcceptedCounter.setOnClickListener { openOrdersList(MainViewModel.ACCEPTED_STATUS) }
        binding.tvReadyCounter.setOnClickListener { openOrdersList(MainViewModel.READY_STATUS) }
        binding.pullToRefresh.setOnRefreshListener { viewModel.getOrders() }
        binding.ivMenu.setOnClickListener { (requireActivity() as MainNavigationActivity).binding.drawerLayout.openDrawer(GravityCompat.START) }
    }

    private fun openOrdersList(status: Int) {
        startActivity(Intent(requireContext(), OrdersListActivity::class.java).apply { putExtra(OrdersListActivity.STATUS_KEY, status) })
    }

    private fun setObservers() {
        viewModel.onLogoutSucceeded.observe(viewLifecycleOwner) { startActivity(Intent(requireContext(), LoginActivity::class.java).newTask()) }
        viewModel.newOrdersLiveData.observe(viewLifecycleOwner) {
            binding.tvNoNewOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvNewOrders.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvNewOrders.adapter = NewOrdersAdapter(it.toMutableList(), onRequestClicked)
            binding.pullToRefresh.isRefreshing = false
        }
        viewModel.acceptedOrdersLiveData.observe(viewLifecycleOwner) {
            binding.tvNoAcceptedOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvAcceptedOrders.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvAcceptedOrders.adapter = RespondedOrdersAdapter(it.toMutableList(), onRequestClicked)
            binding.pullToRefresh.isRefreshing = false
        }
        viewModel.readyOrdersLiveData.observe(viewLifecycleOwner) {
            binding.tvNoReadyOrdersFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            binding.rvReadyOrders.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvReadyOrders.adapter = RespondedOrdersAdapter(it.toMutableList(), onRequestClicked)
            binding.pullToRefresh.isRefreshing = false
        }
        viewModel.overviewCountsLiveData.observe(viewLifecycleOwner) { counts ->
            val zeroCount = "0"
            binding.tvNewCounter.text = counts?.filter { it.status == MainViewModel.NEW }?.getOrNull(0)?.total?.toString() ?: zeroCount
            binding.tvAcceptedCounter.text = counts?.filter { it.status == MainViewModel.ACCEPTED }?.getOrNull(0)?.total?.toString() ?: zeroCount
            binding.tvReadyCounter.text = counts?.filter { it.status == MainViewModel.READY }?.getOrNull(0)?.total?.toString() ?: zeroCount
            binding.pullToRefresh.isRefreshing = false
        }
        viewModel.branchStatusMutableLiveData.observe(viewLifecycleOwner) {
            binding.tvSellerStatus.text = if (it?.isOpened != false) "Open" else "Closed"
            val drawable = ContextCompat.getDrawable(requireContext(), if (it?.isOpened != false) R.drawable.ic_green_baseline_circle_24 else R.drawable.ic_red_baseline_circle_24)
            binding.tvSellerStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
            (((requireActivity() as MainNavigationActivity).binding.navView.getHeaderView(0) as ViewGroup)[0] as TextView).text = it.sellerName
        }
    }
}

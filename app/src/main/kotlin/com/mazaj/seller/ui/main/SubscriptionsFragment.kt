package com.mazaj.seller.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.base.BaseFragment
import com.mazaj.seller.databinding.FragmentSubscriptionsBinding
import com.mazaj.seller.extensions.getRequiredIntent
import com.mazaj.seller.ui.main.viewModel.MainViewModel
import com.mazaj.seller.ui.shared.network.OnFetchingData
import com.mazaj.seller.ui.shared.pagination.PaginationView

class SubscriptionsFragment : BaseFragment(), OnFetchingData, PaginationView {
    override val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val binding by lazy { FragmentSubscriptionsBinding.inflate(layoutInflater) }
    override val recyclerView: RecyclerView by lazy { binding.rvItems }
    private val onRequestClicked: (Long, String) -> (Unit) = { value, key -> startActivity(requireContext().getRequiredIntent(key, value)) }
    private val subscriptionsAdapter = MySubscriptionsAdapter(mutableListOf(), onRequestClicked)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnFetchingData()
        setupRecyclerViewPagination()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = subscriptionsAdapter
        }
        setListeners()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFirstPage()
    }

    private fun setListeners() {
        binding.icMenu.setOnClickListener { (requireActivity() as MainNavigationActivity).binding.drawerLayout.openDrawer(GravityCompat.START) }
    }

    private fun setObservers() {
        viewModel.itemList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvNoItemsFound.visibility = View.VISIBLE
                subscriptionsAdapter.updateList(it)
                recyclerView.visibility = View.GONE
            } else {
                binding.tvNoItemsFound.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                subscriptionsAdapter.updateList(it)
            }
        }
    }
}

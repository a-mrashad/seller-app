package com.mazaj.seller.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.base.BaseFragment
import com.mazaj.seller.databinding.FragmentSubscriptionsBinding
import com.mazaj.seller.extensions.getRequiredIntent
import com.mazaj.seller.ui.main.viewModel.MainViewModel
import com.mazaj.seller.ui.shared.network.OnFetchingData

class SubscriptionsFragment : BaseFragment(), OnFetchingData {
    override val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private val binding by lazy { FragmentSubscriptionsBinding.inflate(layoutInflater) }
    private val onRequestClicked: (Long, String) -> (Unit) = { value, key -> startActivity(requireContext().getRequiredIntent(key, value)) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnFetchingData()
        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        setListeners()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSubscriptions()
    }

    private fun setListeners() {
        binding.icMenu.setOnClickListener { (requireActivity() as MainNavigationActivity).binding.drawerLayout.openDrawer(GravityCompat.START) }
    }

    private fun setObservers() {
        viewModel.subscriptionsLiveData.observe(viewLifecycleOwner) {
            binding.rvItems.adapter = MySubscriptionsAdapter(it.toMutableList(), onRequestClicked)
            binding.tvNoItemsFound.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }
}

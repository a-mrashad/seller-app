package com.mazaj.seller.ui.subscriptionDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.base.BaseFragment
import com.mazaj.seller.databinding.FragmentSubscriptionsDaysBinding
import com.mazaj.seller.ui.orderDetails.viewModel.OrderDetailsViewModel

class SubscriptionDaysFragment : BaseFragment() {
    override val viewModel by lazy { ViewModelProvider(requireActivity())[OrderDetailsViewModel::class.java] }
    private val binding by lazy { FragmentSubscriptionsDaysBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.subscriptionOrderDetailsLiveData.observe(viewLifecycleOwner) {
            binding.rvDays.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = SubscriptionDaysAdapter(it.subscriptions)
            }
        }
    }
}

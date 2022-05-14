package com.mazaj.seller.ui.subscriptionDetails

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateMargins
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.Constants
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseFragment
import com.mazaj.seller.databinding.ActivityOrderDetailsBinding
import com.mazaj.seller.repository.networking.models.SubscriptionOrder
import com.mazaj.seller.repository.networking.models.SubscriptionsDetailsResponse
import com.mazaj.seller.ui.main.viewModel.MainViewModel
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.orderDetails.view.SubscriptionOrderItemsAdapter
import com.mazaj.seller.ui.orderDetails.viewModel.OrderDetailsViewModel
import com.mazaj.seller.ui.shared.network.OnFormSubmitted
import org.joda.time.DateTime

class SubscriptionDetailsFragment : BaseFragment(), OnFormSubmitted {
    override val viewModel by lazy { ViewModelProvider(requireActivity())[OrderDetailsViewModel::class.java] }
    private val binding by lazy { ActivityOrderDetailsBinding.inflate(layoutInflater) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        setObservers()
        setupOnFormSubmitted()
        requireActivity().intent.extras!!.getLong(OrderDetailsActivity.SUBSCRIPTION_ID_KEY, -1).apply { viewModel.getSubscriptionDetails(this) }
        listOf(
            binding.activityOrderDetailPickup, binding.tvOrderNumber, binding.tvOrderDate, binding.tvTypeOrder,
            binding.tvTypeSubscription, binding.tvDecline, binding.icBack, binding.tvTotalItemsCount
        ).forEach { it.visibility = View.GONE }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() = binding.apply {
        btnAcceptOrder.setOnClickListener { viewModel.onActionButtonClicked() }
    }

    private fun setObservers() {
        viewModel.subscriptionOrderDetailsLiveData.observe(viewLifecycleOwner) { setSubscriptionData(it) }
        viewModel.onOrderAccepted.observe(viewLifecycleOwner) { requireActivity().finish() }
    }

    private fun setSubscriptionData(subscription: SubscriptionsDetailsResponse?) = binding.apply {
        val deliveryJob = subscription?.subscriptions?.filter {
            it.isCurrent == true
        }?.getOrNull(0) ?: subscription?.subscriptions?.getOrNull(0) ?: return this
        tvPaymentType.text = subscription?.paymentStatusLabel
        val totalCountText = "${subscription?.itemsCount} ${getString(R.string.items)}"
        tvTotalPrice.text = "${subscription?.totalPrice} ${getString(R.string.currency)}"
        tvTotalCount.text = totalCountText
        tvVatDetails.text = subscription?.vatDetails
        val orderPickupDate = deliveryJob.deliveryAt?.minus(DateTime.now().millis)?.millis?.div(Constants.MINUTE)
        val orderPickupRemainingMinutes = if (orderPickupDate ?: -1 < 0) 0 else orderPickupDate
        tvPickupTime.text = "$orderPickupRemainingMinutes ${getString(R.string.minute)}"
        subscription?.items?.let { handleSubscriptionOrderItems(it.toMutableList()) }
        handleSubscriptionButton(subscription)
    }

    private fun handleSubscriptionButton(order: SubscriptionsDetailsResponse?) = binding.btnAcceptOrder.apply {
        if (order?.isAccepted != true) {
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.green))
            binding.tvLetsDoIt.text = getText(R.string.accept_let_do_it)
        } else if (order.subscriptions.any { it.isCurrent == true } && order.subscriptions.find { it.isCurrent == true }?.isMarkedReadyToPickup != false) {
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), getActionButtonColor(R.color.sky_blue)))
            binding.tvLetsDoIt.text = getText(R.string.ready_label)
        } else {
            visibility = View.GONE
        }
    }

    private fun handleSubscriptionOrderItems(subscriptionOrder: MutableList<SubscriptionOrder>) {
        binding.rvOrderDetails.apply {
            val params = layoutParams as ConstraintLayout.LayoutParams
            layoutParams = params.apply { updateMargins(top = 0) }
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = SubscriptionOrderItemsAdapter(subscriptionOrder)
        }
    }

    private fun getActionButtonColor(status: Int): Int = if (status == MainViewModel.NEW_ACCEPTANCE_STATUS) R.color.green else R.color.sky_blue
}

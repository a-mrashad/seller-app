package com.mazaj.seller.ui.orderDetails.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mazaj.seller.Constants.MINUTE
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseActivity
import com.mazaj.seller.databinding.ActivityOrderDetailsBinding
import com.mazaj.seller.repository.networking.models.OrderDetailResponse
import com.mazaj.seller.repository.networking.models.OrderItem
import com.mazaj.seller.repository.networking.models.SubscriptionOrder
import com.mazaj.seller.repository.networking.models.SubscriptionsDetailsResponse
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.NEW_ACCEPTANCE_STATUS
import com.mazaj.seller.ui.orderDetails.viewModel.OrderDetailsViewModel
import com.mazaj.seller.ui.shared.network.OnFormSubmitted
import org.joda.time.DateTime

class OrderDetailsActivity : BaseActivity(), OnFormSubmitted {
    private val binding by lazy { ActivityOrderDetailsBinding.inflate(layoutInflater) }
    override val viewModel by lazy { ViewModelProvider(this)[OrderDetailsViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setObservers()
        setupOnFormSubmitted()
        intent.extras!!.getLong(SUBSCRIPTION_ID_KEY, -1).apply { if (this != -1L) viewModel.getSubscriptionDetails(this) }
        intent.extras!!.getLong(ID_KEY, -1).apply { if (this != -1L) viewModel.getOrderDetail(this) }
    }

    private fun setListeners() = binding.apply {
        icBack.setOnClickListener { onBackPressed() }
        btnAcceptOrder.setOnClickListener { viewModel.onActionButtonClicked() }
        tvDecline.setOnClickListener { viewModel.declineOrder() }
    }

    private fun setObservers() {
        viewModel.orderDetailsLiveData.observe(this, Observer { setOrderData(it) })
        viewModel.subscriptionOrderDetailsLiveData.observe(this, Observer { setSubscriptionData(it) })
        viewModel.onOrderAccepted.observe(this, Observer { finish() })
    }

    private fun setSubscriptionData(order: SubscriptionsDetailsResponse?) {
        binding.apply {
            val deliveryJob = order?.subscriptions?.filter { it.isCurrent == true }?.getOrNull(0) ?: order?.subscriptions?.getOrNull(0) ?: return
            tvOrderNumber.text = deliveryJob.subscriptionNo
            tvOrderType.visibility = if (order?.type == 1) View.VISIBLE else View.GONE
            tvSubscriptionType.visibility = if (order?.type == 2) View.VISIBLE else View.GONE
            val orderPickupDate = deliveryJob.deliveryAt?.minus(DateTime.now().millis)?.millis?.div(MINUTE)
            val orderPickupRemainingMinutes = if (orderPickupDate ?: -1 < 0) 0 else orderPickupDate
            tvOrderDate.text =
                StringBuilder().append("Pickup in $orderPickupRemainingMinutes ")
                    .append(getString(R.string.minute))
                    .append(PIPE)
                    .append(deliveryJob.deliveryAt?.toString(DTF))
                    .toString()
            if (deliveryJob.deliveryAt?.minus(DateTime.now().millis)?.millis ?: 0 < MINUTE.toLong()) {
                tvOrderDate.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@OrderDetailsActivity, R.color.light_red))
                tvOrderDate.setTextColor(ContextCompat.getColor(this@OrderDetailsActivity, R.color.white))
            }
            val totalCountText = "${order?.items?.size} ${getString(R.string.items)}"
            tvItemsCount.text = totalCountText
            tvTotalPrice.text = "${order?.totalPrice} ${getString(R.string.currency)}"
            tvPickupTime.text = "$orderPickupRemainingMinutes ${getString(R.string.minute)}"
            tvTotalCount.text = totalCountText
        }
        order?.items?.let { handleSubscriptionOrderItems(it.toMutableList()) }
        handleSubscriptionButton(order)
    }

    private fun handleSubscriptionButton(order: SubscriptionsDetailsResponse?) = binding.btnAcceptOrder.apply {
        if (order?.isAccepted != true) {
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@OrderDetailsActivity, R.color.green))
            binding.tvLetsDoIt.text = getText(R.string.accept_let_do_it)
            binding.tvDecline.visibility = View.VISIBLE
        } else if (order.subscriptions.any { it.isCurrent == true } && order.subscriptions.find { it.isCurrent == true }?.isMarkedReadyToPickup != true) {
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@OrderDetailsActivity, getActionButtonColor(R.color.sky_blue)))
            binding.tvLetsDoIt.text = getText(R.string.ready_label)
            binding.tvDecline.visibility = View.GONE
        } else {
            visibility = View.GONE
            binding.tvDecline.visibility = View.GONE
        }
    }

    private fun handleSubscriptionOrderItems(subscriptionOrder: MutableList<SubscriptionOrder>) {
        binding.rvOrderDetails.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = SubscriptionOrderItemsAdapter(subscriptionOrder)
        }
    }

    private fun setOrderData(order: OrderDetailResponse) {
        binding.apply {
            tvOrderNumber.text = order.orderNumber
            tvOrderType.text = order.typeLabel
            val orderPickupDate = order.pickupAt.minus(DateTime.now().millis).millis / MINUTE
            val orderPickupRemainingMinutes = if (orderPickupDate < 0) 0 else orderPickupDate
            tvOrderDate.text =
                StringBuilder().append("Pickup in $orderPickupRemainingMinutes ")
                    .append(getString(R.string.minute))
                    .append(PIPE)
                    .append(order.pickupAt.toString(DTF))
                    .toString()
            if (order.pickupAt.minus(DateTime.now().millis).millis < MINUTE.toLong()) {
                tvOrderDate.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@OrderDetailsActivity, R.color.light_red))
                tvOrderDate.setTextColor(ContextCompat.getColor(this@OrderDetailsActivity, R.color.white))
            }
            val totalCountText = "${order.items?.size} ${getString(R.string.items)}"
            tvItemsCount.text = totalCountText
            tvTotalPrice.text = "${order.totalPrice} ${getString(R.string.currency)}"
            tvPickupTime.text = "$orderPickupRemainingMinutes ${getString(R.string.minute)}"
            // TODO Handle order Vat Text
            tvTotalCount.text = totalCountText
        }
        order.items?.let { handleOrderItems(it) }
        handleOrderButton(order.acceptanceStatus!!)
    }

    private fun handleOrderItems(items: MutableList<OrderItem>) {
        binding.rvOrderDetails.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = OrderItemsAdapter(items)
        }
    }

    private fun handleOrderButton(acceptanceStatus: Int) {
        binding.btnAcceptOrder.apply {
            backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this@OrderDetailsActivity, getActionButtonColor(acceptanceStatus)))
            binding.tvLetsDoIt.text = getText(getActionButtonText(acceptanceStatus))
        }
        binding.tvDecline.visibility = if (acceptanceStatus == NEW_ACCEPTANCE_STATUS) View.VISIBLE else View.GONE
    }

    private fun getActionButtonColor(status: Int): Int = if (status == NEW_ACCEPTANCE_STATUS) R.color.green else R.color.sky_blue

    private fun getActionButtonText(status: Int): Int = if (status == NEW_ACCEPTANCE_STATUS) R.string.accept_let_do_it else R.string.ready_label

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

    companion object {
        const val ID_KEY = "id"
        const val SUBSCRIPTION_ID_KEY = "subscription_id"
        const val PIPE = " | "
        const val DTF = "hh:mma"
    }
}

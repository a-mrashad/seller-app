package com.mazaj.seller.ui.orderDetails.view

import android.content.res.ColorStateList
import android.os.Bundle
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
import com.mazaj.seller.ui.orderDetails.viewModel.OrderDetailsViewModel
import org.joda.time.DateTime

class OrderDetailsActivity : BaseActivity() {
    private val binding by lazy { ActivityOrderDetailsBinding.inflate(layoutInflater) }
    override val viewModel by lazy { ViewModelProvider(this)[OrderDetailsViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setListeners()
        setObservers()
        viewModel.getOrderDetail(intent.extras!!.getString("id", "0"))
    }

    private fun setListeners() = binding.apply {
        icBack.setOnClickListener { onBackPressed() }
        btnAcceptOrder.setOnClickListener { viewModel.acceptOrder() }
    }

    private fun setObservers() {
        viewModel.orderDetailLiveData.observe(this, Observer { setOrderData(it) })
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
                    .append(" | ")
                    .append(order.pickupAt.toString("hh:mma"))
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
    }

    private fun handleOrderItems(items: MutableList<OrderItem>) {
        binding.rvOrderDetails.apply {
            layoutManager = LinearLayoutManager(this@OrderDetailsActivity, LinearLayoutManager.VERTICAL, false)
            adapter = OrderItemsAdapter(items)
        }
    }
}

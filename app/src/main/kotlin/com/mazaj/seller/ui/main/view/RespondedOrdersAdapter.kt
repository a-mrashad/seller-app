package com.mazaj.seller.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.Constants.MINUTE
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemRespondedRequestBinding
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.ID_KEY
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.SUBSCRIPTION_ID_KEY
import org.joda.time.DateTime

class RespondedOrdersAdapter(
    private val items: MutableList<Order>,
    private val onClick: (Long, String) -> Unit,
    private val isFullScreen: Boolean = false
) : RecyclerView.Adapter<RespondedOrdersAdapter.RespondedOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RespondedOrdersAdapter.RespondedOrderViewHolder =
        RespondedOrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_responded_request, parent, false))

    override fun onBindViewHolder(holder: RespondedOrdersAdapter.RespondedOrderViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.count()

    inner class RespondedOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRespondedRequestBinding.bind(itemView)

        fun bind(order: Order) {
            if (isFullScreen) {
                val params = binding.root.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                binding.root.layoutParams = params
            }
            binding.root.setOnClickListener {
                if (order.type == 1) onClick(order.id, ID_KEY) else onClick(order.orderId!!, SUBSCRIPTION_ID_KEY)
            }
            binding.apply {
                tvOrderId.text = "#${order.orderNumber}"
                tvPickupTime.text = "${order.deliveryAt?.minus(DateTime.now().millis)?.millis?.div(MINUTE)}"
                tvPaymentStatus.text = order.paymentStatusLabel
                tvItemsCount.text = "${order.itemsCount} items"
            }
        }
    }
}

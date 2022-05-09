package com.mazaj.seller.ui.main.view

import android.view.View
import android.view.ViewGroup
import com.mazaj.seller.Constants.MINUTE
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemRespondedRequestBinding
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.ID_KEY
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.SUBSCRIPTION_ID_KEY
import com.mazaj.seller.ui.shared.pagination.ListItem
import com.mazaj.seller.ui.shared.pagination.PagedAdapter
import com.mazaj.seller.ui.shared.pagination.PagedViewHolder
import org.joda.time.DateTime

class RespondedOrdersAdapter(
    override var itemList: List<ListItem>,
    private val onClick: (Long, String) -> Unit,
    private val isFullScreen: Boolean = false
) : PagedAdapter<PagedViewHolder>() {
    override val itemLayoutId = R.layout.item_responded_request

    override fun bind(item: ListItem, holder: PagedViewHolder) {
        val binding = ItemRespondedRequestBinding.bind(holder.itemView)
        binding.apply {
            val order = item as Order
            if (isFullScreen) {
                val params = root.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                root.layoutParams = params
            }
            root.setOnClickListener {
                if (order.type == 1) onClick(order.id, ID_KEY) else onClick(order.orderId!!, SUBSCRIPTION_ID_KEY)
            }
            tvOrderId.text = "#${order.orderNumber}"
            tvPickupTime.text = "${order.deliveryAt.minus(DateTime.now().millis)?.millis?.div(MINUTE)}"
            tvOrderTypeOneTime.visibility = if (order.type == 1) View.VISIBLE else View.GONE
            tvOrderTypeSubscription.visibility = if (order.type == 2) View.VISIBLE else View.GONE
            tvPaymentStatus.text = order.paymentStatusLabel
            tvItemsCount.text = "${order.itemsCount} items"
        }
    }

    override fun isSimilar(oldItem: ListItem, newItem: ListItem) = (oldItem as? Order)?.id == (newItem as? Order)?.id
}

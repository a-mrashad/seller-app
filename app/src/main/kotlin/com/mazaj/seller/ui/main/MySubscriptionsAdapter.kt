package com.mazaj.seller.ui.main

import android.view.View
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemSubscriptionsBinding
import com.mazaj.seller.repository.networking.models.MySubscriptionData
import com.mazaj.seller.ui.main.viewModel.MainViewModel.Companion.ACCEPTED_SUBSCRIPTION_STATUS
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.shared.pagination.ListItem
import com.mazaj.seller.ui.shared.pagination.PagedAdapter
import com.mazaj.seller.ui.shared.pagination.PagedViewHolder

class MySubscriptionsAdapter(override var itemList: List<ListItem>, private val onRequestClicked: (Long, String) -> (Unit)) : PagedAdapter<PagedViewHolder>() {
    override val itemLayoutId = R.layout.item_subscriptions

    override fun bind(item: ListItem, holder: PagedViewHolder) {
        val binding = ItemSubscriptionsBinding.bind(holder.itemView)
        binding.apply {
            val subscription = (item as MySubscriptionData).subscriptions.find { it.isCurrent == true } ?: item.subscriptions.first()
            tvOrderId.text = subscription.subscriptionNo
            tvItemsCount.text = "${item.itemsCount} items"
            tvPickupDate.text = subscription.deliveryAt?.toString("hh:mm a")
            tvAccepted.visibility = if (item.status == ACCEPTED_SUBSCRIPTION_STATUS) View.VISIBLE else View.GONE
            tvCompleted.visibility = if (item.status == ACCEPTED_SUBSCRIPTION_STATUS) View.GONE else View.VISIBLE
            btnViewSubscriptionDetails.setOnClickListener { onRequestClicked(item.id, OrderDetailsActivity.SUBSCRIPTION_ID_KEY) }
        }
    }

    override fun isSimilar(oldItem: ListItem, newItem: ListItem) = (oldItem as? MySubscriptionData)?.id == (newItem as? MySubscriptionData)?.id
}

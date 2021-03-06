package com.mazaj.seller.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemNewRequestBinding
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.ID_KEY
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.SUBSCRIPTION_ID_KEY

class NewOrdersAdapter(
    private val items: MutableList<Order>,
    private val onClick: (Long, String) -> Unit,
    private val isFullScreen: Boolean = false
) : RecyclerView.Adapter<NewOrdersAdapter.NewOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewOrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_new_request, parent, false))

    override fun onBindViewHolder(holder: NewOrdersAdapter.NewOrderViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.count()

    inner class NewOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNewRequestBinding.bind(itemView)

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
                tvItemsCount.text = "${order.itemsCount} items"
                tvOrderTypeOneTime.visibility = if (order.type == 1) View.VISIBLE else View.GONE
                tvOrderTypeSubscription.visibility = if (order.type == 2) View.VISIBLE else View.GONE
            }
        }
    }
}

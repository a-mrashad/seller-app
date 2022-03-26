package com.mazaj.seller.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemNewRequestBinding
import com.mazaj.seller.repository.networking.models.Order

class NewOrdersAdapter(
    private val items: MutableList<Order>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<NewOrdersAdapter.NewOrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewOrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_new_request, parent, false))

    override fun onBindViewHolder(holder: NewOrdersAdapter.NewOrderViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.count()

    inner class NewOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNewRequestBinding.bind(itemView)

        fun bind(order: Order) {
            binding.root.setOnClickListener { onClick(order.id) }
            binding.apply {
                tvOrderId.text = "#${order.orderNumber}"
                tvItemsCount.text = "${order.itemsCount} items"
            }
        }
    }
}

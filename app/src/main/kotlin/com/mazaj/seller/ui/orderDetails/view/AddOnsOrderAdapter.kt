package com.mazaj.seller.ui.orderDetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemAddOnsBinding
import com.mazaj.seller.repository.networking.models.OrderAddOns

class AddOnsOrderAdapter(private val items: MutableList<OrderAddOns>) : RecyclerView.Adapter<AddOnsOrderAdapter.OrderAddOnsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAddOnsViewHolder =
        OrderAddOnsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_ons, parent, false))

    override fun onBindViewHolder(holder: OrderAddOnsViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class OrderAddOnsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemAddOnsBinding.bind(itemView)

        fun bind(orderAddOns: OrderAddOns) {
            binding.apply {
                itemAddOnsText.text = orderAddOns.enName ?: orderAddOns.name
            }
        }
    }
}

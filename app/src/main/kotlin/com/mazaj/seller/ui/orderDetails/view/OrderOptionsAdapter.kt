package com.mazaj.seller.ui.orderDetails.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import com.mazaj.seller.databinding.ItemOrderOptionsBinding
import com.mazaj.seller.repository.networking.models.OrderOptions

class OrderOptionsAdapter(private val items: MutableList<OrderOptions>) : RecyclerView.Adapter<OrderOptionsAdapter.OrderOptionsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderOptionsViewHolder =
        OrderOptionsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order_options, parent, false))

    override fun onBindViewHolder(holder: OrderOptionsViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class OrderOptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemOrderOptionsBinding.bind(itemView)

        fun bind(orderOptions: OrderOptions) {
            binding.apply {
                tvOptionName.text = orderOptions.itemOption?.enName ?: orderOptions.name
                tvOptionSize.text = orderOptions.enName
            }
        }
    }
}

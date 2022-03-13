package com.mazaj.seller.ui.shared.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mazaj.seller.R
import java.lang.RuntimeException

const val VIEW_TYPE_LOADING = 0
private const val VIEW_TYPE_NORMAL = 1

abstract class PagedAdapter<T : PagedViewHolder> : RecyclerView.Adapter<PagedViewHolder>() {
    abstract val itemLayoutId: Int
    open var itemList: List<ListItem> = emptyList()

    override fun getItemViewType(position: Int) = if (itemList[position] is LoadingItem) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = when (viewType) {
            VIEW_TYPE_NORMAL -> inflater.inflate(itemLayoutId, parent, false)
            VIEW_TYPE_LOADING -> inflater.inflate(R.layout.item_loading, parent, false)
            else -> throw RuntimeException("View type $viewType is not found")
        }
        return PagedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PagedViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_LOADING) return
        bind(itemList[position], holder)
    }

    abstract fun bind(item: ListItem, holder: PagedViewHolder)

    override fun getItemCount() = itemList.size

    open fun updateList(newList: List<ListItem>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = isSimilar(itemList[oldItemPosition], newList[newItemPosition])
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = sameContents(itemList[oldItemPosition], newList[newItemPosition])
            override fun getOldListSize() = itemList.size
            override fun getNewListSize() = newList.size
        })
        itemList = newList
        diffResult.dispatchUpdatesTo(this as RecyclerView.Adapter<*>)
    }

    open fun sameContents(oldItem: ListItem, newItem: ListItem) = oldItem == newItem

    abstract fun isSimilar(oldItem: ListItem, newItem: ListItem): Boolean
}

open class PagedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

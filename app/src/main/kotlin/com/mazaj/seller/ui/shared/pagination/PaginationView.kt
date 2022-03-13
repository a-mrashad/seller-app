package com.mazaj.seller.ui.shared.pagination

import androidx.recyclerview.widget.RecyclerView

private const val SCROLL_DIRECTION_UP = -1
private const val SCROLL_DIRECTION_DOWN = 1

interface PaginationView {
    val viewModel: PaginationViewModel
    val recyclerView: RecyclerView

    fun setupRecyclerViewPagination(reverse: Boolean = false) {
        if (reverse.not()) recyclerView.itemAnimator = null
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (endPageReached(reverse, dy, recyclerView)) viewModel.loadNextPage()
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun endPageReached(reverse: Boolean, dy: Int, recyclerView: RecyclerView) =
        reverse && dy < 0 && !recyclerView.canScrollVertically(SCROLL_DIRECTION_UP) ||
                !reverse && dy > 0 && !recyclerView.canScrollVertically(SCROLL_DIRECTION_DOWN)
}

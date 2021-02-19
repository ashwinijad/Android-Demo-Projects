package com.example.paginationdemo1.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener protected constructor(layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private val layoutManager: LinearLayoutManager
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount: Int = layoutManager.getChildCount()
        val totalItemCount: Int = layoutManager.getItemCount()
        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
            ) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun totalPageCount(): Int
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */
    init {
        this.layoutManager = layoutManager
    }
}

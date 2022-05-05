package com.mazaj.seller.ui.shared.network

import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseView
import com.mazaj.seller.base.BaseViewModel

interface OnFetchingData : BaseView {
    val viewModel: BaseViewModel

    fun setupOnFetchingData() = viewModel.isScreenLoading.observe(activity, Observer { if (it) showProgressBar() else hideProgressBar() })

    fun showProgressBar() {
        activity.apply {
            progressBarView()?.visibility = View.VISIBLE
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    fun hideProgressBar() = activity.apply {
        progressBarView()?.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun progressBarView(): View? = activity.findViewById(R.id.loadingBar)
}

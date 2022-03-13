package com.mazaj.seller.ui.shared.network

import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.Observer
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseView
import com.mazaj.seller.base.BaseViewModel

interface OnFormSubmitted : BaseView {
    val viewModel: BaseViewModel
    val progressBarView: View?
        get() = activity.findViewById(R.id.progressBar)
    val progressPercentTextView: TextView
        get() = activity.findViewById(R.id.progress_percent) as TextView

    fun setupOnFormSubmitted() {
        viewModel.isFormLoading.observe(activity, Observer { if (it) showProgressBar() else hideProgressBar() })
        viewModel.uploadProgressLiveData.observe(activity, Observer { updateProgress(it) })
    }

    fun showProgressBar() = activity.apply {
        progressBarView?.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideProgressBar() = activity.apply {
        progressBarView?.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun updateProgress(progress: Int?) = progressPercentTextView.apply {
        visibility = View.VISIBLE
        text = "$progress %"
    }
}

package com.mazaj.seller.ui.shared.network

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.mazaj.seller.R
import com.mazaj.seller.base.BaseView

interface NetworkStatusView : BaseView {
    val viewModel: NetworkStatusViewModel
    val netStatusText: TextView?
        get() = activity.findViewById(R.id.connection_status)

    fun setupNetStatusView() {
        viewModel.networkAvailability.observe(activity, Observer { connected: Boolean ->
            netStatusText?.apply {
                visibility = View.VISIBLE
                background = ContextCompat.getDrawable(activity, if (connected) R.color.secondaryColor else R.color.red)
                text = activity.getString(if (connected) R.string.internet_is_back else R.string.no_internet)
            }
        })
        viewModel.hideInternetStatus.observe(activity, Observer { netStatusText?.visibility = View.GONE })
    }
}

package com.mazaj.seller.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mazaj.seller.Constants.NOTIFICATION_EVENT_NAME
import com.mazaj.seller.Constants.NOTIFICATION_ORDER_KEY
import com.mazaj.seller.repository.networking.models.Order
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.NotificationView
import com.mazaj.seller.ui.shared.ShowMessageView
import com.mazaj.seller.ui.shared.network.NetworkStatusView
import java.util.*

abstract class BaseActivity : AppCompatActivity(), ShowMessageView, NetworkStatusView, NotificationView {
    abstract override val viewModel: BaseViewModel
    private val connectivityManager by lazy { getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    private val customNetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) = viewModel.onConnectivityChanged(false)
        override fun onAvailable(network: Network) = viewModel.onConnectivityChanged(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupShowMessageView()
        setupNetStatusView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onViewAppeared()
        connectivityManager.registerNetworkCallback(NetworkRequest.Builder().build(), customNetworkCallback)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(customNetworkCallback)
    }

    override fun onResume() {
        super.onResume()
        val eventFilter = IntentFilter()
        eventFilter.addAction(NOTIFICATION_EVENT_NAME)
        registerReceiver(notificationReceiver, eventFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(notificationReceiver)
    }

    override fun attachBaseContext(context: Context?) {
        val locale = Locale(repository.appPreferences.currentLanguage)
        Locale.setDefault(locale)
        val configuration: Configuration = context?.resources?.configuration?.apply { setLocale(locale) } ?: return super.attachBaseContext(context)
        super.attachBaseContext(context.createConfigurationContext(configuration))
    }

    private val notificationReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.extras?.let {
//                val text = it.getString(NOTIFICATION_ORDER_KEY) ?: ""
//                val order = Json.decodeFromString(Order.serializer(), text.substring(12, text.length-2).replace("\\", ""))
//                order
//                val order = Json.decodeFromString(
//                    NotificationOrderResponse.serializer(),
//                    it.getString(NOTIFICATION_ORDER_KEY, "")
//                )
                setupNotificationView(it.getSerializable(NOTIFICATION_ORDER_KEY) as? Order)
            }
        }
    }
}

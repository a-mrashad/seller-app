package com.mazaj.seller.base

import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mazaj.seller.repository.repository
import com.mazaj.seller.ui.shared.ShowMessageView
import com.mazaj.seller.ui.shared.network.NetworkStatusView
import java.util.*

abstract class BaseActivity : AppCompatActivity(), ShowMessageView, NetworkStatusView {
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

    override fun attachBaseContext(context: Context?) {
        val locale = Locale(repository.appPreferences.currentLanguage)
        Locale.setDefault(locale)
        val configuration: Configuration = context?.resources?.configuration?.apply { setLocale(locale) } ?: return super.attachBaseContext(context)
        super.attachBaseContext(context.createConfigurationContext(configuration))
    }
}

package com.mazaj.seller.extensions

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.mazaj.seller.R

fun AppCompatActivity.launchCustomTabOrAvailableBrowser(url: String) {
    if (customTabSupported(url)) {
        launch(url)
    } else {
        openBrowserIntent(url)
    }
}

@SuppressLint("QueryPermissionsNeeded")
private fun AppCompatActivity.customTabSupported(url: String): Boolean {
    val resolvedActivityList = packageManager.queryIntentActivities(Intent(Intent.ACTION_VIEW, Uri.parse(url)), 0)

    return resolvedActivityList.any { info ->
        val serviceIntent = Intent().apply {
            action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
            `package` = info.activityInfo.packageName
        }
        packageManager.resolveService(serviceIntent, 0) != null
    }
}

private fun AppCompatActivity.launch(url: String) {
    val toolbarColor = ContextCompat.getColor(this, R.color.primaryColor)
    CustomTabsIntent.Builder().apply { setDefaultColorSchemeParams(CustomTabColorSchemeParams.Builder().setToolbarColor(toolbarColor).build()) }
        .build().apply {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            launchUrl(applicationContext, Uri.parse(url))
        }
}

fun AppCompatActivity.openBrowserIntent(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    try {
        startActivity(this, browserIntent, null)
    } catch (e: ActivityNotFoundException) {
        showSnackBar(message = getString(R.string.shared_message_no_browser_installed))
    }
}

package com.mazaj.seller.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mazaj.seller.R
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity
import com.mazaj.seller.ui.orderDetails.view.OrderDetailsActivity.Companion.ID_KEY
import com.mazaj.seller.ui.subscriptionDetails.SubscriptionDetailsActivity
import com.mazaj.seller.utils.AlertDialogContent

fun AppCompatActivity.showAlertDialog(alertDialogContent: AlertDialogContent) {
    val builder = AlertDialog.Builder(this)
        .setCancelable(alertDialogContent.cancelable)
        .setMessage(alertDialogContent.body)

    alertDialogContent.title.ifNotBlank { builder.setTitle(it) }
    alertDialogContent.positiveText.ifNotBlank { builder.setPositiveButton(it, alertDialogContent.yesListener) }
    alertDialogContent.negativeText.ifNotBlank { builder.setNegativeButton(it, alertDialogContent.noListener) }

    builder.create().show()
}

fun AppCompatActivity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this, message, duration).show()

fun AppCompatActivity.showError(status: Int, message: String = "", view: View? = findViewById(R.id.error_layout)) { view?.apply {
    visibility = status
    findViewById<TextView>(R.id.tvStatus).text = message
} }

fun AppCompatActivity.showSnackBar(view: View = findViewById(android.R.id.content), message: String, duration: Int = Snackbar.LENGTH_SHORT) =
    Snackbar.make(view, message, duration).show()

fun AppCompatActivity.showNetworkStatus(view: View = findViewById(R.id.connection_status), status: Int) { view.visibility = status }

fun AppCompatActivity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
}

fun AppCompatActivity.showDeclineReasonDialog(onSubmit: (String) -> Unit) {
    val contentView = layoutInflater.inflate(R.layout.dialog_decline_reason, this.findViewById(R.id.dialog_container), false)
    val builder = AlertDialog.Builder(this).apply { setView(contentView) }
    val editTextDescription = contentView.findViewById<TextInputEditText>(R.id.edDescription)
    val dialog = builder.create().apply {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        show()
    }
    editTextDescription.doAfterTextChanged {
        val button = contentView.findViewById<MaterialButton>(R.id.button_submit)
        button.isEnabled = it.toString().isPresent()
        button.alpha = if (it.toString().isPresent()) 1F else 0.3F
    }
    contentView.findViewById<ImageView>(R.id.image_view_close).setOnClickListener { dialog.dismiss() }
    contentView.findViewById<MaterialButton>(R.id.button_submit).setOnClickListener {
        onSubmit(editTextDescription.text.toString())
        dialog.dismiss()
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Context.getRequiredIntent(key: String, value: Long) = Intent(
    this,
    if (key == ID_KEY) OrderDetailsActivity::class.java else SubscriptionDetailsActivity::class.java
).apply { putExtra(key, value) }

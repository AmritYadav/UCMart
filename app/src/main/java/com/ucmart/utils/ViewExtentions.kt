package com.ucmart.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ucmart.R

/**
 * To be used with ProgressBar
 *
 * sets visibility state of the view to View.VISIBLE and
 * also disables user interaction while loading data
 */
fun View.visible(window: Window?) {
    this.visibility = View.VISIBLE
    window.disableViewInteraction()
}

/**
 * To be used with ProgressBar
 *
 * sets visibility state of the view to View.GONE and
 * also enables user interaction when data loading is complete
 */
fun View.gone(window: Window?) {
    this.visibility = View.GONE
    window.enableViewInteraction()
}

fun View.visibleGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun ImageView.loadOriginalImage(imgUrl: String?) {
    Glide.with(this)
        .asBitmap()
        .load(imgUrl)
        .error(R.drawable.ic_product_placeholder)
        .placeholder(R.drawable.ic_product_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

private fun Window?.disableViewInteraction() {
    this?.let {
        setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }
}

private fun Window?.enableViewInteraction() {
    this?.let { clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE) }
}

fun TextView.formatString(stringRes: Int, value: String = "") {
    return this.setText(
        HtmlCompat.fromHtml(
            this.context.getString(stringRes, value),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    )
}

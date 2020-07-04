package jp.neechan.akari.dictionary.base_ui.presentation.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(messageResource: Int) {
    toast(getString(messageResource))
}

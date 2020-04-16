package jp.neechan.akari.dictionary.common

import android.content.Context
import android.widget.Toast

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun toast(context: Context, messageResource: Int) {
    toast(context, context.getString(messageResource))
}

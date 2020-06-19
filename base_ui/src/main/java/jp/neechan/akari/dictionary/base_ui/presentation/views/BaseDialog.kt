package jp.neechan.akari.dictionary.base_ui.presentation.views

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

abstract class BaseDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    override fun onStart() {
        super.onStart()
        setupDialogWidth()
    }

    private fun setupDialogWidth() {
        dialog?.window?.apply {
            val dialogWidthRatio = 0.9f
            val width = (resources.displayMetrics.widthPixels * dialogWidthRatio).toInt()
            setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}
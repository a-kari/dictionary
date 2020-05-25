package jp.neechan.akari.dictionary.base.presentation.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

/**
 * Spinner extension that calls onItemSelected even when the selection
 * is the same as its previous value.
 */
class ReselectableSpinner : AppCompatSpinner {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun setSelection(position: Int) {
        val isSameSelected = position == selectedItemPosition
        super.setSelection(position)
        maybeFireReselectedEvent(isSameSelected, position)
    }

    override fun setSelection(position: Int, animate: Boolean) {
        val isSameSelected = position == selectedItemPosition
        super.setSelection(position, animate)
        maybeFireReselectedEvent(isSameSelected, position)
    }

    // Spinner does not call the OnItemSelectedListener if the same item is selected,
    // so maybe do it manually now.
    private fun maybeFireReselectedEvent(isSameSelected: Boolean, position: Int) {
        if (isSameSelected) {
            onItemSelectedListener?.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }
}
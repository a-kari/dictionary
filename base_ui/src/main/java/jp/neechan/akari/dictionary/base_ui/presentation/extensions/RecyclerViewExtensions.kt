package jp.neechan.akari.dictionary.base_ui.presentation.extensions

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addVerticalDividers() {
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}

package jp.neechan.akari.dictionary.discover

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addVerticalDividers() {
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}
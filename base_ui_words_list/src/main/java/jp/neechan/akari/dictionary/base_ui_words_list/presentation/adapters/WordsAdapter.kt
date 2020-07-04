package jp.neechan.akari.dictionary.base_ui_words_list.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.neechan.akari.dictionary.base_ui_words_list.R
import kotlinx.android.synthetic.main.item_word.view.*

open class WordsAdapter(private val wordActionListener: WordActionListener) :
    PagedListAdapter<String, WordsAdapter.WordHolder>(diffCallback) {

    interface WordActionListener {
        fun onWordClicked(word: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordHolder(root, wordActionListener)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    open class WordHolder(
        private val root: View,
        private val wordActionListener: WordActionListener
    ) : RecyclerView.ViewHolder(root) {

        // A property to consume in child modules.
        protected val deleteButton: ImageView get() = root.deleteButton

        fun bind(word: String) {
            root.avatarView.setText(word)
            root.wordTv.text = word
            root.setOnClickListener { wordActionListener.onWordClicked(word) }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}

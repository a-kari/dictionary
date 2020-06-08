package jp.neechan.akari.dictionary.feature_home.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.neechan.akari.dictionary.base_ui_words_list.presentation.adapters.WordsAdapter
import jp.neechan.akari.dictionary.feature_home.R

class EditableWordsAdapter(private val wordActionListener: WordActionListener) : WordsAdapter(wordActionListener) {

    private var isEditMode = false

    interface WordActionListener : WordsAdapter.WordActionListener {
        fun onWordDeleted(word: String)
    }

    fun toggleEditMode(isEditMode: Boolean) {
        this.isEditMode = isEditMode
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordHolder(root, wordActionListener)
    }

    override fun onBindViewHolder(holder: WordsAdapter.WordHolder, position: Int) {
        getItem(position)?.let { (holder as WordHolder).bind(it, isEditMode) }
    }

    class WordHolder(
        private val root: View,
        private val wordActionListener: WordActionListener
    ) : WordsAdapter.WordHolder(root, wordActionListener) {

        fun bind(word: String, isEditMode: Boolean) {
            super.bind(word)

            if (!isEditMode) {
                deleteButton.visibility = View.GONE
                deleteButton.setOnClickListener(null)

            } else {
                root.setOnClickListener(null)
                deleteButton.visibility = View.VISIBLE
                deleteButton.setOnClickListener { wordActionListener.onWordDeleted(word) }
            }
        }
    }
}
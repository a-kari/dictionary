package jp.neechan.akari.dictionary.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.Word
import jp.neechan.akari.dictionary.common.WordsAdapter
import kotlinx.android.synthetic.main.item_word.view.*

class EditableWordsAdapter(private val wordActionListener: WordActionListener) : WordsAdapter(wordActionListener) {

    private var isEditMode = false

    interface WordActionListener : WordsAdapter.WordActionListener {
        fun onWordDeleted(word: Word)
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
        (holder as WordHolder).bind(words[position], isEditMode)
    }

    class WordHolder(private val root: View,
                     private val wordActionListener: WordActionListener) : WordsAdapter.WordHolder(root, wordActionListener) {

        fun bind(word: Word, isEditMode: Boolean) {
            super.bind(word)

            if (!isEditMode) {
                root.deleteButton.visibility = View.GONE
                root.deleteButton.setOnClickListener(null)

            } else {
                root.setOnClickListener(null)
                root.deleteButton.visibility = View.VISIBLE
                root.deleteButton.setOnClickListener { wordActionListener.onWordDeleted(word) }
            }
        }
    }
}
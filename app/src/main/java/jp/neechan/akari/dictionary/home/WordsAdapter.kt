package jp.neechan.akari.dictionary.home

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.Word
import kotlinx.android.synthetic.main.item_word.view.*

// todo: 1. Split into WordsAdapter and EditableWordsAdapter.
// todo: 2. DiffUtils.
class WordsAdapter(private val wordActionListener: WordActionListener) : RecyclerView.Adapter<WordsAdapter.WordHolder>() {

    private val words = mutableListOf<Word>()
    private var isEditMode = false

    interface WordActionListener {
        fun onWordClicked(word: Word)
        fun onWordDeleted(word: Word)
    }

    fun addWords(words: List<Word>) {
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    fun toggleEditMode(isEditMode: Boolean) {
        this.isEditMode = isEditMode
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = words.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordHolder(root, wordActionListener)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind(words[position], isEditMode)
    }

    class WordHolder(private val root: View,
                     private val wordActionListener: WordActionListener) : RecyclerView.ViewHolder(root) {

        fun bind(word: Word, isEditMode: Boolean) {
            root.avatarView.setText(word.word)
            root.wordTv.text = word.word

            if (!isEditMode) {
                root.deleteButton.visibility = GONE
                root.deleteButton.setOnClickListener(null)
                root.setOnClickListener { wordActionListener.onWordClicked(word) }

            } else {
                root.setOnClickListener(null)
                root.deleteButton.visibility = VISIBLE
                root.deleteButton.setOnClickListener { wordActionListener.onWordDeleted(word) }
            }
        }
    }
}

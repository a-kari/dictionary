package jp.neechan.akari.dictionary.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.neechan.akari.dictionary.R
import kotlinx.android.synthetic.main.item_word.view.*

// todo: DiffUtils.
open class WordsAdapter(private val wordActionListener: WordActionListener) : RecyclerView.Adapter<WordsAdapter.WordHolder>() {

    protected val words = mutableListOf<String>()

    interface WordActionListener {
        fun onWordClicked(word: String)
    }

    fun addWords(words: List<String>) {
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = words.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordHolder(root, wordActionListener)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind(words[position])
    }

    open class WordHolder(private val root: View,
                          private val wordActionListener: WordActionListener) : RecyclerView.ViewHolder(root) {

        open fun bind(word: String) {
            root.avatarView.setText(word)
            root.wordTv.text = word
            root.setOnClickListener { wordActionListener.onWordClicked(word) }
        }
    }
}

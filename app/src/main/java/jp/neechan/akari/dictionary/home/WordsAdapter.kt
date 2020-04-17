package jp.neechan.akari.dictionary.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.Word
import kotlinx.android.synthetic.main.item_word.view.*

class WordsAdapter(private val wordClickListener: (Word) -> Unit) : RecyclerView.Adapter<WordsAdapter.WordHolder>() {

    private val words = mutableListOf<Word>()

    // todo: Use DiffUtils here!
    fun addWords(words: List<Word>) {
        this.words.addAll(words)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = words.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordHolder(root, wordClickListener)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind(words[position])
    }

    class WordHolder(private val root: View,
                     private val wordClickListener: (Word) -> Unit) : RecyclerView.ViewHolder(root) {

        fun bind(word: Word) {
            root.avatarView.setText(word.word)
            root.wordTv.text = word.word
            root.setOnClickListener { wordClickListener(word) }
        }
    }
}

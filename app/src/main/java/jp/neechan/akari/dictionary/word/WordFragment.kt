package jp.neechan.akari.dictionary.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.BaseFragment
import jp.neechan.akari.dictionary.common.Word
import jp.neechan.akari.dictionary.common.toast
import kotlinx.android.synthetic.main.fragment_word.*

// todo: Change TTSHelper to TTSService. Move interaction with TTS to WordViewModel!
// todo: TTSService should be global, retrieve locale and voice from a repository.
// todo: And stop speaking in onDestroy() via ViewModel, too.
class WordFragment : BaseFragment() {

    private lateinit var word: Word
    private var addToDictionaryEnabled = false

    companion object {
        fun newInstance() = WordFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_word, container, false)
    }

    // todo: Here also should be a function that receives a word.word and retrieves the word from a repository.
    fun setWord(word: Word) {
        this.word = word
    }

    fun setAddToDictionaryEnabled(addToDictionaryEnabled: Boolean) {
        this.addToDictionaryEnabled = addToDictionaryEnabled
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordView.setWord(word)

        if (addToDictionaryEnabled) {
            addToDictionaryButton.visibility = VISIBLE
            addToDictionaryButton.setOnClickListener { toast(requireContext(), "Adding to your dictionary...") }

        } else {
            addToDictionaryButton.visibility = GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        wordView.destroy()
    }
}
package jp.neechan.akari.dictionary.home

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.DividerItemDecoration
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.BaseFragment
import jp.neechan.akari.dictionary.common.Word
import jp.neechan.akari.dictionary.common.toast
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), WordsAdapter.WordActionListener{

    private lateinit var wordsAdapter: WordsAdapter
    private lateinit var editButton: MenuItem
    private var isEditMode = false // todo: Move the state to ViewModel.

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        wordsAdapter = WordsAdapter(this)
        wordsRv.adapter = wordsAdapter
        wordsRv.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }

    private fun setupObservers() {
        wordsAdapter.addWords(getSampleWords())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)

        editButton = menu.findItem(R.id.edit)
        syncEditButtonIcon()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.edit) {
            toggleEditMode()
            true

        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun toggleEditMode() {
        isEditMode = !isEditMode
        wordsAdapter.toggleEditMode(isEditMode)
        syncEditButtonIcon()
    }

    private fun syncEditButtonIcon() {
        editButton.setIcon(if (!isEditMode) R.drawable.ic_edit else R.drawable.ic_ok)
    }

    override fun onWordClicked(word: Word) {
        toast(requireContext(), word.word)
    }

    override fun onWordDeleted(word: Word) {
        toast(requireContext(), "Deleted: " + word.word)
    }

    private fun getSampleWords(): List<Word> {
        val words = mutableListOf<Word>()
        words.add(Word("Air", "", listOf(), listOf(), 10.0))
        words.add(Word("Nature", "", listOf(), listOf(), 10.0))
        words.add(Word("Cold", "", listOf(), listOf(), 10.0))
        words.add(Word("Rain", "", listOf(), listOf(), 10.0))
        words.add(Word("Star", "", listOf(), listOf(), 10.0))
        words.add(Word("Fire", "", listOf(), listOf(), 10.0))
        words.add(Word("Forest", "", listOf(), listOf(), 10.0))
        words.add(Word("Lightning", "", listOf(), listOf(), 10.0))
        words.add(Word("Snow", "", listOf(), listOf(), 10.0))
        words.add(Word("Mountain", "", listOf(), listOf(), 10.0))
        words.add(Word("Lake", "", listOf(), listOf(), 10.0))
        words.add(Word("Ocean", "", listOf(), listOf(), 10.0))
        words.add(Word("Sea", "", listOf(), listOf(), 10.0))
        return words
    }
}
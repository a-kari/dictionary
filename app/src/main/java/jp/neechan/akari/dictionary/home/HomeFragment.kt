package jp.neechan.akari.dictionary.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import jp.neechan.akari.dictionary.R
import jp.neechan.akari.dictionary.common.BaseFragment
import jp.neechan.akari.dictionary.common.addVerticalDividers
import jp.neechan.akari.dictionary.common.getSampleWordsString
import jp.neechan.akari.dictionary.common.toast
import jp.neechan.akari.dictionary.search.SearchWordActivity
import jp.neechan.akari.dictionary.word.WordActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), EditableWordsAdapter.WordActionListener{

    private lateinit var wordsAdapter: EditableWordsAdapter
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
        setupListeners()
    }

    private fun setupRecyclerView() {
        wordsAdapter = EditableWordsAdapter(this)
        wordsRv.adapter = wordsAdapter
        wordsRv.addVerticalDividers()
    }

    private fun setupObservers() {
        wordsAdapter.addWords(getSampleWordsString())
    }

    private fun setupListeners() {
        addWordButton.setOnClickListener {
            val searchIntent = Intent(requireContext(), SearchWordActivity::class.java)
            startActivity(searchIntent)
        }
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

    override fun onWordClicked(word: String) {
        val wordIntent = Intent(requireContext(), WordActivity::class.java)
        wordIntent.putExtra(WordActivity.EXTRA_WORD, word)
        wordIntent.putExtra(WordActivity.EXTRA_ADD_TO_DICTIONARY_ENABLED, false)
        startActivity(wordIntent)
    }

    override fun onWordDeleted(word: String) {
        toast(requireContext(), "Deleted: $word")
    }
}
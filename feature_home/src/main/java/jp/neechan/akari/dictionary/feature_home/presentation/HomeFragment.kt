package jp.neechan.akari.dictionary.feature_home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import jp.neechan.akari.dictionary.base_ui.presentation.extensions.addVerticalDividers
import jp.neechan.akari.dictionary.base_ui.presentation.views.BaseFragment
import jp.neechan.akari.dictionary.core_api.di.AppWithFacade
import jp.neechan.akari.dictionary.core_api.presentation.mediators.SearchWordMediator
import jp.neechan.akari.dictionary.core_api.presentation.mediators.WordMediator
import jp.neechan.akari.dictionary.core_api.presentation.models.UIState
import jp.neechan.akari.dictionary.feature_home.R
import jp.neechan.akari.dictionary.feature_home.di.HomeComponent
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), EditableWordsAdapter.WordActionListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeViewModel

    @Inject lateinit var searchWordMediator: SearchWordMediator
    @Inject lateinit var wordMediator: WordMediator

    private lateinit var wordsAdapter: EditableWordsAdapter
    private lateinit var editButton: MenuItem

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeComponent.create((requireActivity().application as AppWithFacade).getFacade()).inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

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
        viewModel.wordsLiveData.observe(viewLifecycleOwner, Observer { words ->
            wordsAdapter.submitList(words)
        })

        viewModel.uiStateLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is UIState.ShowContent -> showContent()
                is UIState.ShowNotFoundError -> showEmptyContent()
                is UIState.ShowError -> showError(state.errorMessage)
            }
        })
    }

    private fun showContent() {
        progressBar.visibility = GONE
        noWordsTv.visibility = GONE
        errorTv.visibility = GONE
        wordsRv.visibility = VISIBLE
        maybeShowFab()
    }

    private fun showEmptyContent() {
        progressBar.visibility = GONE
        errorTv.visibility = GONE
        wordsRv.visibility = GONE
        noWordsTv.visibility = VISIBLE
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorTv.setText(errorMessage)
        progressBar.visibility = GONE
        noWordsTv.visibility = GONE
        wordsRv.visibility = GONE
        errorTv.visibility = VISIBLE
    }

    private fun setupListeners() {
        addWordButton.setOnClickListener { searchWordMediator.openSearchWord(requireContext()) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)

        editButton = menu.findItem(R.id.edit)
        syncEditMode()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.edit) {
            viewModel.isEditMode = !viewModel.isEditMode
            syncEditMode()
            true

        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun syncEditMode() {
        wordsAdapter.toggleEditMode(viewModel.isEditMode)
        editButton.setIcon(if (!viewModel.isEditMode) R.drawable.ic_edit else R.drawable.ic_ok)
    }

    override fun onWordClicked(word: String) {
        wordMediator.openWordActivity(requireContext(), word)
    }

    override fun onWordDeleted(word: String) {
        viewModel.deleteWord(word)
    }

    /**
     * Here we consider a case when a user scrolls down and the fab hides.
     * Then he deletes some words and the RecyclerView becomes non-scrollable,
     * so he can't return the fab back with scrolling up. So we need to check
     * if the RecyclerView has become non-scrollable and if so, then return the fab back.
     */
    private fun maybeShowFab() {
        if (!wordsRv.canScrollVertically()) {
            val coordinatorLayoutParams = addWordButton.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = coordinatorLayoutParams.behavior as ScrollAwareFABBehavior
            behavior.show(addWordButton)
        }
    }

    /** Is RecyclerView can be scrolled vertically up or down? */
    private fun RecyclerView.canScrollVertically(): Boolean {
        return canScrollVertically(-1) || canScrollVertically(1)
    }
}
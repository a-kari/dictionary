package jp.neechan.akari.dictionary.base.presentation.views

import androidx.fragment.app.Fragment
import jp.neechan.akari.dictionary.base.presentation.viewmodels.ViewModelFactory
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment() {

    // todo: Different ViewModelFactories for different modules.
    protected val viewModelFactory: ViewModelFactory by inject()
}
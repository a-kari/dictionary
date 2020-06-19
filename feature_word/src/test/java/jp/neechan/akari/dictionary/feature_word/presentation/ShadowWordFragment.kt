package jp.neechan.akari.dictionary.feature_word.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow.directlyOn
import org.robolectric.shadow.api.Shadow.invokeConstructor
import org.robolectric.util.ReflectionHelpers.ClassParameter

/**
 * This shadow class:
 *
 * 1. Counts how many times WordFragment instances were created.
 * 2. Stubs onCreate(), onCreateView(), onViewCreated() to disable them in other classes' tests.
 */
@Implements(WordFragment::class)
@Suppress("unused")
internal class ShadowWordFragment {

    @RealObject lateinit var realWordFragment: WordFragment

    companion object {

        /**
         * Counts how many times WordFragment instances were created in a test.
         * Should be torn down after every test.
         */
        @Volatile var createdTimes = 0
            private set

        fun incrementCreatedTimes() = synchronized(createdTimes) {
            ++createdTimes
        }

        fun teardownCreatedTimes() = synchronized(createdTimes) {
            createdTimes = 0
        }
    }

    @Implementation
    @Suppress("TestFunctionName")
    fun __constructor__() {
        incrementCreatedTimes()
        invokeConstructor(WordFragment::class.java, realWordFragment)
    }

    @Implementation
    fun onCreate(savedInstanceState: Bundle?) {
        // Call the parent and do nothing
        directlyOn<Unit, Fragment>(
            realWordFragment as Fragment,
            Fragment::class.java,
            "onCreate",
            ClassParameter.from(Bundle::class.java, savedInstanceState)
        )
    }

    @Implementation
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Do nothing
        return null
    }

    @Implementation
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Do nothing
    }
}
package jp.neechan.akari.dictionary.feature_home.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton.OnVisibilityChangedListener

internal class ScrollAwareFABBehavior(context: Context?, attrs: AttributeSet?) :
    FloatingActionButton.Behavior(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        fab: FloatingActionButton,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        fab: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(coordinatorLayout, fab, target, dxConsumed, dyConsumed,
            dxUnconsumed, dyUnconsumed, type, consumed)

        // User is scrolling down, need to hide the FAB.
        if (dyConsumed > 0 && fab.visibility == View.VISIBLE) {
            hide(fab)

        // User is scrolling up, need to show the FAB.
        } else if (dyConsumed < 0 && fab.visibility != View.VISIBLE) {
            show(fab)
        }
    }

    fun hide(fab: FloatingActionButton) {
        fab.hide(object : OnVisibilityChangedListener() {
            override fun onHidden(fab: FloatingActionButton) {
                super.onHidden(fab)
                fab.visibility = View.INVISIBLE
            }
        })
    }

    fun show(fab: FloatingActionButton) {
        fab.show()
    }
}

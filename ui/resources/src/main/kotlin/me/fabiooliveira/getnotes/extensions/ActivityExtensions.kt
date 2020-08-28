package me.fabiooliveira.getnotes.extensions

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityOptionsCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

private const val SHARED_ELEMENT_NAME = "shared_element_end_root"

fun Activity.setContainerTransformAnimationFrom(@LayoutRes layoutRes: Int) {
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
    setContentView(layoutRes)
}

fun Activity.setContainerTransformAnimationTo(mainContent: View, rootView: View) {
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    mainContent.transitionName = SHARED_ELEMENT_NAME
    setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementEnterTransition = buildContainerTransformTransparent(mainContent, rootView)
    window.sharedElementReturnTransition = buildContainerTransformTransparent(mainContent, rootView)
    setContentView(mainContent)
}

fun Activity.createActivityOptionsAnimation(view: View) = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this, view, SHARED_ELEMENT_NAME
)

fun Activity.hideKeyboardFrom(view: View) {
    val imm: InputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

private fun buildContainerTransformTransparent(mainContent: View, rootView: View) =
        MaterialContainerTransform().apply {
            addTarget(mainContent)
            setAllContainerColors(MaterialColors.getColor(rootView, ui.resources.R.attr.colorSurface))
            pathMotion = MaterialArcMotion()
            duration = 500
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN
        }
package me.fabiooliveira.getnotes.presentation.extensions

import android.view.View
import android.view.animation.AlphaAnimation

private const val DELAY_CODE_TRANSITION = 200L
private const val NO_OPACITY = 0f
private const val FULL_OPACITY = 1f

fun View.fadeOut() {
    AlphaAnimation(FULL_OPACITY, NO_OPACITY).also {
        it.duration = DELAY_CODE_TRANSITION
        visibility = View.GONE
        startAnimation(it)
    }
}

fun View.fadeIn() {
    AlphaAnimation(NO_OPACITY, FULL_OPACITY).also {
        it.duration = DELAY_CODE_TRANSITION
        visibility = View.VISIBLE
        startAnimation(it)
    }
}

package me.fabiooliveira.getnotes.extensions

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator

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

fun View.doSlideDownAnimation() {
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.interpolator = DecelerateInterpolator()
    fadeIn.duration = 1200

    val anim = AnimationSet(false)
    anim.addAnimation(fadeIn)
    animation = anim

    val valueAnimator = ValueAnimator.ofFloat(-70f, 0f)
    valueAnimator.interpolator = AccelerateDecelerateInterpolator()
    valueAnimator.duration = 400
    valueAnimator.addUpdateListener {
        val progress = it.animatedValue as Float
        translationY = progress
    }
    valueAnimator.start()
}

fun View.doPopAnimation(duration: Long, func: () -> Unit) {
    animate()
            .apply {
                this.duration = duration
                scaleX(1.04f)
                scaleY(1.04f)
                withEndAction {
                    this.duration = duration
                    scaleX(1f)
                    scaleY(1f)
                    withEndAction {
                        func.invoke()
                    }
                }
                start()
            }
}

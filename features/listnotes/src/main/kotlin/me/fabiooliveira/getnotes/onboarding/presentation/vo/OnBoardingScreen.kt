package me.fabiooliveira.getnotes.onboarding.presentation.vo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

internal data class OnBoardingScreen(
        @DrawableRes val imageRes: Int,
        @StringRes val titleRes: Int,
        @StringRes val descriptionRes: Int
)
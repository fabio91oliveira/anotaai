package me.fabiooliveira.getnotes.onboarding.presentation.action

import me.fabiooliveira.getnotes.onboarding.presentation.vo.OnBoardingScreen

internal sealed class OnBoardingActions {
    data class ShowScreens(
            val list: List<OnBoardingScreen>
    ) : OnBoardingActions()

    object CloseScreen : OnBoardingActions()
}
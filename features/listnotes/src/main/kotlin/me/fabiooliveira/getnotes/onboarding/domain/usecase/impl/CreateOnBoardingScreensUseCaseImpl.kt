package me.fabiooliveira.getnotes.onboarding.domain.usecase.impl

import features.listnotes.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.fabiooliveira.getnotes.onboarding.domain.usecase.CreateOnBoardingScreensUseCase
import me.fabiooliveira.getnotes.onboarding.presentation.vo.OnBoardingScreen

internal class CreateOnBoardingScreensUseCaseImpl : CreateOnBoardingScreensUseCase {
    override suspend fun invoke(): Flow<MutableList<OnBoardingScreen>> {
        return flow {
            emit(
                    mutableListOf<OnBoardingScreen>().apply {
                        add(
                                OnBoardingScreen(R.drawable.list_notes_features_ic_on_boarding_first_screen,
                                        R.string.list_notes_feature_on_boarding_first_screen_title,
                                        R.string.list_notes_feature_on_boarding_first_screen_description)
                        )

                        add(
                                OnBoardingScreen(R.drawable.list_notes_features_ic_on_boarding_second_screen,
                                        R.string.list_notes_feature_on_boarding_second_screen_title,
                                        R.string.list_notes_feature_on_boarding_second_screen_description)
                        )

                        add(
                                OnBoardingScreen(R.drawable.list_notes_features_ic_on_boarding_third_screen,
                                        R.string.list_notes_feature_on_boarding_third_screen_title,
                                        R.string.list_notes_feature_on_boarding_third_screen_description)
                        )

                        add(
                                OnBoardingScreen(R.drawable.list_notes_features_ic_on_boarding_fourth_screen,
                                        R.string.list_notes_feature_on_boarding_fourth_screen_title,
                                        R.string.list_notes_feature_on_boarding_fourth_screen_description)
                        )
                    }
            )
        }
    }
}
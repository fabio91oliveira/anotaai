package me.fabiooliveira.getnotes.onboarding.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.fabiooliveira.getnotes.onboarding.domain.usecase.CreateOnBoardingScreensUseCase
import me.fabiooliveira.getnotes.onboarding.presentation.action.OnBoardingActions
import timber.log.Timber


internal class OnBoardingViewModel(
        private val createOnBoardingScreensUseCase: CreateOnBoardingScreensUseCase
) : ViewModel() {

    private val _onBoardingActions by lazy { MutableLiveData<OnBoardingActions>() }
    val onBoardingActions: LiveData<OnBoardingActions> = _onBoardingActions

    init {
        showScreens()
    }

    fun closeScreen() {
        OnBoardingActions.CloseScreen.sendAction()
    }

    private fun showScreens() {
        viewModelScope.launch {
            createOnBoardingScreensUseCase()
                    .flowOn(Dispatchers.Main)
                    .catch {
                        Timber.e(it)
                        OnBoardingActions.CloseScreen.sendAction()
                    }
                    .collect {
                        OnBoardingActions.ShowScreens(it).sendAction()
                    }
        }
    }

    private fun OnBoardingActions.sendAction() {
        _onBoardingActions.value = this
    }
}
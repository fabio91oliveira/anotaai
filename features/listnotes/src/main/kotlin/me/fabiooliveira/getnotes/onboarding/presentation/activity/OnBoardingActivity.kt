package me.fabiooliveira.getnotes.onboarding.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import features.listnotes.R
import kotlinx.android.synthetic.main.list_notes_feature_activity_on_boarding.*
import me.fabiooliveira.getnotes.onboarding.presentation.action.OnBoardingActions
import me.fabiooliveira.getnotes.onboarding.presentation.adapter.OnBoardingAdapter
import me.fabiooliveira.getnotes.onboarding.presentation.viewmodel.OnBoardingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class OnBoardingActivity : AppCompatActivity(R.layout.list_notes_feature_activity_on_boarding) {

    private val viewModel: OnBoardingViewModel by viewModel()

    private val onBoardingAdapter by lazy {
        OnBoardingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setupViewPager()
        setupObservables()
        setupClickListeners()
    }

    private fun setupObservables() {
        with(viewModel) {
            onBoardingActions.observe(this@OnBoardingActivity, Observer {
                when (it) {
                    is OnBoardingActions.ShowScreens -> {
                        onBoardingAdapter.addScreens(it.list)
                        setDotsTabLayout(it.list.size)
                    }
                    is OnBoardingActions.CloseScreen -> finishScreen()
                }
            })
        }
    }

    private fun setupViewPager() {
        with(viewPager) {
            adapter = onBoardingAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateIndicators(position)
                    btnNext.visibility =
                            getVisibility(position != onBoardingAdapter.getFinalPage())
                    btnGoToApp.visibility =
                            getVisibility(position == onBoardingAdapter.getFinalPage())
                }
            })
        }
    }

    private fun setupClickListeners() {
        btnNext.setOnClickListener {
            goNext()
        }
        btnGoToApp.setOnClickListener {
            viewModel.closeScreen()
        }
    }

    private fun updateIndicators(position: Int) {
        tbIndicators.selectTab(tbIndicators.getTabAt(position))
    }

    private fun setDotsTabLayout(count: Int) {
        repeat(count) {
            tbIndicators.addTab(tbIndicators.newTab())
        }
        tbIndicators.touchables.forEach { it.isEnabled = false }
    }

    private fun goNext() {
        viewPager.currentItem = viewPager.currentItem + 1
    }

    private fun getVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.INVISIBLE

    private fun finishScreen() = finish()

    companion object {
        fun newIntent(context: Context) = Intent(
                context,
                OnBoardingActivity::class.java
        )
    }

}
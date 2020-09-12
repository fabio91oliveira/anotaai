package me.fabiooliveira.getnotes.listnotes.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import features.listnotes.R
import kotlinx.android.synthetic.main.list_notes_feature_activity_list_notes.*
import me.fabiooliveira.getnotes.extensions.disableDarkMode
import me.fabiooliveira.getnotes.extensions.doSlideDownAnimation
import me.fabiooliveira.getnotes.extensions.enableDarkMode
import me.fabiooliveira.getnotes.extensions.isDarkMode
import me.fabiooliveira.getnotes.extensions.openDialog
import me.fabiooliveira.getnotes.extensions.whenNull
import me.fabiooliveira.getnotes.listnotes.presentation.action.ListNotesAction
import me.fabiooliveira.getnotes.listnotes.presentation.adapter.CustomFragmentPagerAdapter
import me.fabiooliveira.getnotes.listnotes.presentation.fragment.PastListNotesFragment
import me.fabiooliveira.getnotes.listnotes.presentation.fragment.RecentListNotesFragment
import me.fabiooliveira.getnotes.listnotes.presentation.viewmodel.ListNotesViewModel
import me.fabiooliveira.getnotes.listnotes.presentation.vo.NoteItem
import me.fabiooliveira.getnotes.navigation.CREATE_NOTE_REQUEST_CODE
import me.fabiooliveira.getnotes.navigation.NOTE_ITEM_TAG
import me.fabiooliveira.getnotes.navigation.NoteDetailsNavigation
import me.fabiooliveira.getnotes.onboarding.presentation.activity.OnBoardingActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

private const val TAB_FIRST_POSITION = 0
private const val PERCENTAGE_ZERO = 0F
private const val FULL_ALPHA = 1F
private const val DOUBLE = 2

internal class ListNotesActivity : AppCompatActivity(R.layout.list_notes_feature_activity_list_notes),
        AppBarLayout.OnOffsetChangedListener {

    private val noteDetailsNavigation: NoteDetailsNavigation by inject()
    private val viewModel: ListNotesViewModel by viewModel()
    private val fragmentPagerAdapter by lazy {
        CustomFragmentPagerAdapter(
                supportFragmentManager, lifecycle
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservables()
        setupAdapter()
        setupViewPager()
        setupTabLayout()
        setupSearch()
        setupClickListeners()
        savedInstanceState?.also {
            viewModel.checkIfShowsOnBoarding()
        }.whenNull {
            viewModel.setColorScheme()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_NOTE_REQUEST_CODE) {
                viewModel.getRecentNotesList()
                viewModel.getPastNotesList()
            }
        }

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val percentage = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
        tvHeader.alpha = percentage

        if (percentage == PERCENTAGE_ZERO) {
            etSearch.alpha = FULL_ALPHA
            tlOptions.alpha = FULL_ALPHA
        } else {
            etSearch.alpha = FULL_ALPHA - percentage * DOUBLE
            tlOptions.alpha = FULL_ALPHA - percentage
        }
    }

    private fun setupObservables() {
        with(viewModel) {
            listNotesViewState.observe(this@ListNotesActivity, Observer {
                showAddButton(it.isAddButtonVisible)
                setHeaderTitle(it.tabSelected)
                showHeader(it.isHeaderVisible)
                enableChangeTab(it.isChangeTabEnabled)
            })
            listNotesAction.observe(this@ListNotesActivity, Observer {
                when (it) {
                    is ListNotesAction.GoToEditNote -> openEditNote(it.noteItem, it.viewId)
                    is ListNotesAction.GoToCreateNote -> openCreateNote()
                    is ListNotesAction.SetDarkMode -> setDarkMode()
                    is ListNotesAction.SetLightMode -> setLightMode()
                    is ListNotesAction.ShowOnBoarding -> showOnBoarding()
                }
            })
        }
    }

    private fun setupAdapter() {
        with(fragmentPagerAdapter) {
            addFragment(RecentListNotesFragment.newInstance())
            addFragment(PastListNotesFragment.newInstance())
        }
    }

    private fun setupViewPager() {
        with(vpContent) {
            adapter = fragmentPagerAdapter
            (getChildAt(TAB_FIRST_POSITION) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tlOptions, vpContent) { tab, position ->
            tab.text = getTabText(position)
        }.attach()
    }

    private fun setupSearch() {
        etSearch.setOnClickListener {
            openDialog(R.string.list_notes_feature_coming_soon_title, R.string.list_notes_feature_coming_soon_description)
        }
    }

    private fun setupClickListeners() {
        fbAdd.setOnClickListener {
            viewModel.goToCreateNote()
        }
        ivDarkMode.setOnClickListener {
            viewModel.switchDarkMode(isDarkMode())
        }
        abInside.addOnOffsetChangedListener(this)

    }

    private fun getTabText(position: Int): String {
        return when (position) {
            TAB_FIRST_POSITION -> getString(R.string.list_notes_feature_tab_recents)
            else -> getString(R.string.list_notes_feature_tab_past)
        }
    }

    private fun setHeaderTitle(@StringRes titleRes: Int?) {
        titleRes?.also { tvHeader.setText(it) }
    }

    private fun showAddButton(hasToShow: Boolean) =
            if (hasToShow) fbAdd.show() else fbAdd.hide()

    private fun showHeader(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE
        if (isVisible && ctContent.visibility == View.GONE) showSearchAnimation()
        ctContent.visibility = visibility
        tvHeader.visibility = visibility
    }

    private fun showSearchAnimation() {
        etSearch.doSlideDownAnimation()
        ctContent.doSlideDownAnimation()
    }

    private fun enableChangeTab(isEnabled: Boolean) {
        vpContent.isUserInputEnabled = isEnabled
    }

    private fun openEditNote(noteItem: NoteItem, viewId: Int) {
        val bundle = Bundle().apply {
            putParcelable(NOTE_ITEM_TAG, noteItem)
        }
        noteDetailsNavigation.navigateToFeature(
                this,
                bundle,
                CREATE_NOTE_REQUEST_CODE)
    }

    private fun openCreateNote() {
        noteDetailsNavigation.navigateToFeature(
                this,
                CREATE_NOTE_REQUEST_CODE)
    }

    private fun setDarkMode() {
        enableDarkMode()
    }

    private fun setLightMode() {
        disableDarkMode()
    }

    private fun showOnBoarding() {
        startActivity(OnBoardingActivity.newIntent(this))
    }
}
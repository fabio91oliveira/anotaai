package me.fabiooliveira.getnotes.presentation.activity

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import features.listnotes.R
import kotlinx.android.synthetic.main.list_notes_feature_activity_list_notes.*
import me.fabiooliveira.getnotes.presentation.adapter.CustomFragmentPagerAdapter
import me.fabiooliveira.getnotes.presentation.fragment.PastListNotesFragment
import me.fabiooliveira.getnotes.presentation.fragment.RecentListNotesFragment
import me.fabiooliveira.getnotes.presentation.viewmodel.ListNotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

internal class ListNotesActivity : AppCompatActivity(R.layout.list_notes_feature_activity_list_notes),
        AppBarLayout.OnOffsetChangedListener {

    private val listNotesViewModel: ListNotesViewModel by viewModel()

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
        abInside.addOnOffsetChangedListener(this)
        listNotesViewModel.getNotesList()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val percentage = abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange
        tvHeader.alpha = percentage

        if (percentage == 0f) {
            search.alpha = 1.0f
            tlOptions.alpha = 1.0f
        } else {
            search.alpha = 1.0f - percentage * 2
            tlOptions.alpha = 1.0f - percentage
        }
    }

    private fun setupObservables() {
        with(listNotesViewModel) {
            recentListNotesViewState.observe(this@ListNotesActivity, Observer {
                showAddButton(it.isAddButtonVisible)
            })
            listNotesViewState.observe(this@ListNotesActivity, Observer {
                setHeaderTitle(it.tabSelected)
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

    companion object {
        private const val TAB_FIRST_POSITION = 0
    }
}
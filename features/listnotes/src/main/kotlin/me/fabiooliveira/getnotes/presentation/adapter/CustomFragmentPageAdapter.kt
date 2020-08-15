package me.fabiooliveira.getnotes.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class CustomFragmentPagerAdapter(
        fragmentActivity: FragmentManager,
        lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentActivity, lifecycle) {
    private val fragmentsList: MutableList<Fragment> = mutableListOf()

    fun addFragment(fragment: Fragment) = fragmentsList.add(fragment)

    override fun getItemCount() = fragmentsList.size
    override fun createFragment(position: Int) = fragmentsList[position]
}
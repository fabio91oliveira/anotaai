package me.fabiooliveira.getnotes.onboarding.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import features.listnotes.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_notes_feature_item_screen_on_boarding.*
import me.fabiooliveira.getnotes.onboarding.presentation.vo.OnBoardingScreen

internal class OnBoardingAdapter :
        RecyclerView.Adapter<OnBoardingAdapter.ScreenViewHolder>() {

    private var screensList: List<OnBoardingScreen>? = null

    fun addScreens(screensList: List<OnBoardingScreen>) {
        this.screensList = screensList
        notifyDataSetChanged()
    }

    fun getFinalPage(): Int {
        screensList?.also {
            return it.size - 1
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ScreenViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.list_notes_feature_item_screen_on_boarding,
                    parent,
                    false
            )
    )

    override fun getItemCount() = screensList?.size ?: 0

    override fun onBindViewHolder(holder: ScreenViewHolder, position: Int) {
        screensList?.also {
            holder.bind(it[position])
        }
    }

    class ScreenViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        fun bind(onBoardingScreen: OnBoardingScreen) {
            ivTop.setImageResource(onBoardingScreen.imageRes)
            tvTitle.text = containerView.resources.getString(onBoardingScreen.titleRes)
            tvDescription.text = containerView.resources.getString(onBoardingScreen.descriptionRes)
        }
    }
}
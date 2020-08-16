package me.fabiooliveira.getnotes.presentation.vo

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import features.listnotes.R

internal enum class RelevanceEnum(
        val relevanceCode: Int,
        @StringRes val titleRes: Int,
        @ColorRes val textColorRes: Int,
        @ColorRes val backgroundColorRes: Int
) {
    HIGH(3, R.string.list_notes_feature_relevance_high, R.color.color_high_relevance, R.color.color_transparent_high_relevance),
    MEDIUM(2, R.string.list_notes_feature_relevance_medium, R.color.color_medium_relevance, R.color.color_transparent_medium_relevance),
    NORMAL(1, R.string.list_notes_feature_relevance_normal, R.color.color_normal_relevance, R.color.color_transparent_normal_relevance);
}
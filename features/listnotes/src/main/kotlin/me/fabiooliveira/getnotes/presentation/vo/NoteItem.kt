package me.fabiooliveira.getnotes.presentation.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteItem(
        val id: Long,
        val title: String,
        val description: String,
        val date: String,
        val dateName: String,
        val relevance: RelevanceEnum,
        val isToday: Boolean
) : Parcelable
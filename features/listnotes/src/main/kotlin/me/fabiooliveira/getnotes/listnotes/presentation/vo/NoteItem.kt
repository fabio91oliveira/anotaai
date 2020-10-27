package me.fabiooliveira.getnotes.listnotes.presentation.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class NoteItem(
        val id: Long,
        val title: String,
        val description: String,
        val calendar: Calendar,
        val dateName: String,
        val relevance: RelevanceEnum,
        val isToday: Boolean,
        val isReminder: Boolean
) : Parcelable
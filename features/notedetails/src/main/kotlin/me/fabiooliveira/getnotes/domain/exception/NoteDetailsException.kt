package me.fabiooliveira.getnotes.domain.exception

import androidx.annotation.StringRes
import features.notedetails.R

sealed class NoteDetailsException(
        @StringRes val titleRes: Int,
        @StringRes val descriptionRes: Int
) : Throwable() {

    object EmptyFieldsException : NoteDetailsException(
            titleRes = R.string.note_details_feature_empty_fields_note_title,
            descriptionRes = R.string.note_details_feature_empty_fields_note_message
    )

    object DateAndTimeBeforeThanTodayException : NoteDetailsException(
            titleRes = R.string.note_details_feature_invalid_reminder_title,
            descriptionRes = R.string.note_details_feature_invalid_reminder_message
    )

}
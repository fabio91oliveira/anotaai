package me.fabiooliveira.getnotes.domain.analytics

internal interface NoteDetailsAnalytics {
    fun trackScreen()
    fun trackScreenMode(isEdit: Boolean)
    fun trackReminderClicked(isEnabled: Boolean)
    fun trackReminderScheduled()
    fun trackReminderCancelled()
    fun trackCloseButtonClicked()
    fun trackPublishButtonClicked()
    fun trackSaveButtonClicked()
    fun trackRemoveButtonClicked()
}
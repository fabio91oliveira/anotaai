package me.fabiooliveira.getnotes.domain.analytics.impl

import me.fabiooliveira.getnotes.analytics.domain.Analytics
import me.fabiooliveira.getnotes.domain.analytics.NoteDetailsAnalytics

private const val NOTE_DETAILS_SCREEN_VIEWED_EVENT = "note_details_screen_viewed"
private const val SCREEN_MODE_EVENT = "note_details_screen_mode"
private const val SCREEN_MODE_EDIT_PARAM = "edit"
private const val SCREEN_MODE_CREATE_PARAM = "create"
private const val REMINDER_ENABLE_EVENT = "note_details_reminder_clicked"
private const val REMINDER_ENABLE_TRUE_PARAM = "true"
private const val REMINDER_ENABLE_FALSE_PARAM = "false"
private const val REMINDER_SCHEDULED_EVENT = "note_details_reminder_scheduled"
private const val REMINDER_CANCELLED_EVENT = "note_details_reminder_cancelled"
private const val CLOSE_BUTTON_CLICKED_EVENT = "note_details_close_button_clicked"
private const val PUBLISH_BUTTON_CLICKED_EVENT = "note_details_publish_button_clicked"
private const val SAVE_BUTTON_CLICKED_EVENT = "note_details_save_button_clicked"
private const val REMOVE_BUTTON_CLICKED_EVENT = "note_details_remove_button_clicked"

internal class NoteDetailsAnalyticsImpl(
        private val analytics: Analytics
) : NoteDetailsAnalytics {
    override fun trackScreen() {
        analytics.trackEvent(NOTE_DETAILS_SCREEN_VIEWED_EVENT)
    }

    override fun trackScreenMode(isEdit: Boolean) {
        val mode = if (isEdit) SCREEN_MODE_EDIT_PARAM else SCREEN_MODE_CREATE_PARAM
        analytics.trackEventWithParamsDefault(SCREEN_MODE_EVENT, mode)
    }

    override fun trackReminderClicked(isEnabled: Boolean) {
        val reminderEnabledParam = if (isEnabled) REMINDER_ENABLE_TRUE_PARAM else REMINDER_ENABLE_FALSE_PARAM
        analytics.trackEventWithParamsDefault(REMINDER_ENABLE_EVENT, reminderEnabledParam)
    }

    override fun trackReminderScheduled() {
        analytics.trackEvent(REMINDER_SCHEDULED_EVENT)
    }

    override fun trackReminderCancelled() {
        analytics.trackEvent(REMINDER_CANCELLED_EVENT)
    }

    override fun trackCloseButtonClicked() {
        analytics.trackEvent(CLOSE_BUTTON_CLICKED_EVENT)
    }

    override fun trackPublishButtonClicked() {
        analytics.trackEvent(PUBLISH_BUTTON_CLICKED_EVENT)
    }

    override fun trackSaveButtonClicked() {
        analytics.trackEvent(SAVE_BUTTON_CLICKED_EVENT)
    }

    override fun trackRemoveButtonClicked() {
        analytics.trackEvent(REMOVE_BUTTON_CLICKED_EVENT)
    }

}
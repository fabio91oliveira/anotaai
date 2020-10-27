package me.fabiooliveira.getnotes.listnotes.domain.analytics.impl

import me.fabiooliveira.getnotes.analytics.domain.Analytics
import me.fabiooliveira.getnotes.listnotes.domain.analytics.ListNotesAnalytics

private const val UPDATE_POPUP_SCREEN_VIEWED_EVENT = "update_popup_screen_viewed"
private const val UPDATE_POPUP_OK_CLICKED_EVENT = "update_popup_ok_clicked"
private const val UPDATE_POPUP_CANCEL_CLICKED_EVENT = "update_popup_cancel_clicked"
private const val LIST_NOTES_SCREEN_VIEWED_EVENT = "list_notes_screen_viewed"
private const val RECENT_NOTES_TB_VIEWED_EVENT = "list_notes_recent_notes_tab_viewed"
private const val PAST_NOTES_TAB_VIEWED_EVENT = "list_notes_past_notes_tab_viewed"
private const val THEME_MODE_CLICK_EVENT = "list_notes_theme_mode_clicked"
private const val THEME_MODE_LIGHT_PARAM = "light"
private const val THEME_MODE_DARK_PARAM = "dark"
private const val FORCE_UPDATE_TRUE_PARAM = "true"
private const val FORCE_UPDATE_FALSE_PARAM = "false"

internal class ListNotesAnalyticsImpl(
        private val analytics: Analytics
) : ListNotesAnalytics {
    override fun trackScreenListNotes() {
        analytics.trackEvent(LIST_NOTES_SCREEN_VIEWED_EVENT)
    }

    override fun trackTabRecentNotes() {
        analytics.trackEvent(RECENT_NOTES_TB_VIEWED_EVENT)
    }

    override fun trackTabPastNotes() {
        analytics.trackEvent(PAST_NOTES_TAB_VIEWED_EVENT)
    }

    override fun trackChangeThemeMode(isDarkMode: Boolean) {
        val themeMode = if (isDarkMode) THEME_MODE_DARK_PARAM else THEME_MODE_LIGHT_PARAM
        analytics.trackEventWithParamsDefault(THEME_MODE_CLICK_EVENT, themeMode)
    }

    override fun trackUpdatePopUpViewed(isForceUpdate: Boolean) {
        val forceUpdate = if (isForceUpdate) FORCE_UPDATE_TRUE_PARAM else FORCE_UPDATE_FALSE_PARAM
        analytics.trackEventWithParamsDefault(UPDATE_POPUP_SCREEN_VIEWED_EVENT, forceUpdate)
    }

    override fun trackUpdatePopUpOkClicked() {
        analytics.trackEvent(UPDATE_POPUP_OK_CLICKED_EVENT)
    }

    override fun trackUpdatePopUpCancelClicked() {
        analytics.trackEvent(UPDATE_POPUP_CANCEL_CLICKED_EVENT)
    }
}
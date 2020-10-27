package me.fabiooliveira.getnotes.listnotes.domain.analytics

internal interface ListNotesAnalytics {
    fun trackScreenListNotes()
    fun trackTabRecentNotes()
    fun trackTabPastNotes()
    fun trackChangeThemeMode(isDarkMode: Boolean)
    fun trackUpdatePopUpViewed(isForceUpdate: Boolean)
    fun trackUpdatePopUpOkClicked()
    fun trackUpdatePopUpCancelClicked()
}
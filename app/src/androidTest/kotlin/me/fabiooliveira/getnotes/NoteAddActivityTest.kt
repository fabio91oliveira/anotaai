package me.fabiooliveira.getnotes

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import me.fabiooliveira.getnotes.feature.noteAdd.ui.activity.NoteAddActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class NoteAddActivityTest {

    @get: Rule val ruleActivity = IntentsTestRule(NoteAddActivity::class.java, false)
//    @Rule @JvmField val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Test
    fun should_add_note() {
        val calendar = Calendar.getInstance()

        Espresso.onView(ViewMatchers.withId(R.id.etTitle))
                .perform(ViewActions.typeText("Title"))
        Espresso.onView(ViewMatchers.withId(R.id.etDescription))
                .perform(ViewActions.typeText("Description"))



        Espresso.onView(ViewMatchers.withId(R.id.etDate))
                .perform(ViewActions.typeTextIntoFocusedView("12/12/12"))
//        Espresso.onView(ViewMatchers.withId(R.id.bvSave))
//                .perform(ViewActions.click())
    }

    @Test
    fun should_click_about_button_and_show(){
        Espresso.onView(ViewMatchers.withId(R.id.dialogNoNotes))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withContentDescription(R.string.about_dialog_title))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.dialogAbout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun should_click_button_add_note() {
        Espresso.onView(ViewMatchers.withId(R.id.dialogNoNotes))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bvNewNote))
                .perform(ViewActions.click())
        intended(hasComponent(NoteAddActivity::class.java.name))
    }

}
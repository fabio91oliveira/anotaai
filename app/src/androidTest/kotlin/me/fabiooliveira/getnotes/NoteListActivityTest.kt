package me.fabiooliveira.getnotes

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import me.fabiooliveira.getnotes.feature.notesList.ui.activity.NotesListActivity
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import me.fabiooliveira.getnotes.feature.noteAdd.ui.activity.NoteAddActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NoteListActivityTest {

    @get: Rule val ruleActivity = IntentsTestRule(NotesListActivity::class.java, false)
//    @Rule @JvmField val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun initActivity(){
        ruleActivity.activity.supportFragmentManager.beginTransaction()
    }

    @Test
    fun should_click_button_add_note() {
        Espresso.onView(ViewMatchers.withId(R.id.bvNewNote))
                .perform(ViewActions.click())
        intended(hasComponent(NoteAddActivity::class.java.name))
    }

}
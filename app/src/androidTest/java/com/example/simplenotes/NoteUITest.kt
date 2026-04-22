package com.example.simplenotes

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simplenotes.ui.NoteListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun mainScreen_displaysCorrectly() {
        onView(withId(R.id.recycler_notes)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_add_note)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun fabClick_opensEditActivity() {
        onView(withId(R.id.fab_add_note)).perform(click())
        onView(withId(R.id.edit_title)).check(matches(isDisplayed()))
        onView(withId(R.id.edit_content)).check(matches(isDisplayed()))
    }

    @Test
    fun createNote_fillFieldsAndSave() {
        onView(withId(R.id.fab_add_note)).perform(click())
        onView(withId(R.id.edit_title))
            .perform(typeText("Test Note Title"), closeSoftKeyboard())
        onView(withId(R.id.edit_content))
            .perform(typeText("This is test content for the note"), closeSoftKeyboard())
        onView(withId(R.id.action_save)).perform(click())
        onView(withText("Test Note Title")).check(matches(isDisplayed()))
    }

    @Test
    fun createNote_emptyTitle_showsError() {
        onView(withId(R.id.fab_add_note)).perform(click())
        onView(withId(R.id.edit_content))
            .perform(typeText("Content without title"), closeSoftKeyboard())
        onView(withId(R.id.action_save)).perform(click())
        onView(withId(R.id.edit_title))
            .check(matches(hasErrorText("Заголовок обязателен")))
    }

    @Test
    fun clickNote_opensEditWithData() {
        onView(withId(R.id.fab_add_note)).perform(click())
        onView(withId(R.id.edit_title))
            .perform(typeText("Editable Note"), closeSoftKeyboard())
        onView(withId(R.id.edit_content))
            .perform(typeText("Original content"), closeSoftKeyboard())
        onView(withId(R.id.action_save)).perform(click())
        onView(withText("Editable Note")).perform(click())
        onView(withId(R.id.edit_title)).check(matches(withText("Editable Note")))
        onView(withId(R.id.edit_content)).check(matches(withText("Original content")))
    }

    @Test
    fun editNote_updateTitle() {
        onView(withId(R.id.fab_add_note)).perform(click())
        onView(withId(R.id.edit_title)).perform(typeText("Before Edit"), closeSoftKeyboard())
        onView(withId(R.id.action_save)).perform(click())
        onView(withText("Before Edit")).perform(click())
        onView(withId(R.id.edit_title))
            .perform(clearText(), typeText("After Edit"), closeSoftKeyboard())
        onView(withId(R.id.action_save)).perform(click())
        onView(withText("After Edit")).check(matches(isDisplayed()))
    }

    @Test
    fun scrollNotesList() {
        for (i in 1..5) {
            onView(withId(R.id.fab_add_note)).perform(click())
            onView(withId(R.id.edit_title)).perform(typeText("Note $i"), closeSoftKeyboard())
            onView(withId(R.id.action_save)).perform(click())
        }

        onView(withId(R.id.recycler_notes))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
    }

    @Test
    fun emptyState_showsMessage() {
        onView(withId(R.id.text_empty_state)).check(matches(isDisplayed()))
        onView(withId(R.id.text_empty_state)).check(matches(withText("Нет заметок")))
    }
}

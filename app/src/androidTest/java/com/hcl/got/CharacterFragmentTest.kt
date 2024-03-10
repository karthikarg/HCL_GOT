package com.hcl.got

import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hcl.got.ui.activity.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CharacterFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setUp() {

    }

    @Test
    @UiThread
    fun ChracterListViewCheck() {

        ActivityScenario.launch(MainActivity::class.java)
            .onActivity { it.updateCharsView("Test",
                listOf("https://www.anapioficeandfire.com/api/characters/2")) }


        Thread.sleep(2000)

        Espresso.onView(withId(R.id.bookNameTV))
            .check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.charactersTV))
            .check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.charactersRecyclerView))
            .check(matches(isDisplayed()))

    }


    @Test
    @UiThread
    fun bookNameTextviewVisibilityCheck() {

        Thread.sleep(2000)

        ActivityScenario.launch(MainActivity::class.java)
            .onActivity { it.updateCharsView("A Game of Thrones",
                listOf("https://www.anapioficeandfire.com/api/characters/2")) }

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withText("Book : A Game of Thrones"))
            .check(matches(isDisplayed()))

    }

    @Test
    @UiThread
    fun characterListFirstItemCheck() {

        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText("Name: Walder"))
            .check(matches(isDisplayed()))

    }

    @Test
    @UiThread
    fun checkProgressBarVisibilityCheck() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        Espresso.onView(ViewMatchers.withContentDescription(appContext.getString(R.string.navigation_drawer_open)))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))


        Espresso.onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2,
                ViewActions.click()
            ))

        Espresso.onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
    }

    @Test
    @UiThread
    fun characterListScrollCheck() {


        Thread.sleep(5000)
        Espresso.onView(withId(R.id.charactersRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(14))

        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withText("Name: Aemon Targaryen"))
            .check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.charactersRecyclerView))
            .check(matches(hasDescendant(withId(R.id.bookNameTextView))))

    }

    @Test
    @UiThread
    fun characterListItemVisibilityCheck() {

        Thread.sleep(5000)
        Espresso.onView(withId(R.id.charactersRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2)) // Scrolls to the third item (position starts from 0)


        Espresso.onView(withId(R.id.charactersRecyclerView))
            .check(matches(hasDescendant(withId(R.id.bookNameTextView))))
        Espresso.onView(withId(R.id.charactersRecyclerView))
            .check(matches(hasDescendant(withId(R.id.bookTitlesTextView))))

        Espresso.onView(withId(R.id.charactersRecyclerView))
            .check(matches(hasDescendant(withId(R.id.bookAliasesTextView))))

    }



}
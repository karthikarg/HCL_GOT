package com.hcl.got

import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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
    fun BookNametextviewVisibilityCheck() {

        ActivityScenario.launch(MainActivity::class.java)
            .onActivity { it.updateCharsView("Test",
                listOf("https://www.anapioficeandfire.com/api/characters/2")) }


        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withText("Book : Test"))
            .check(matches(isDisplayed()))


    }

    @Test
    @UiThread
    fun CharacterListFirstItemCheck() {

        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withText("Name: Walder"))
            .check(matches(isDisplayed()))


    }

    @Test
    @UiThread
    fun CharacterListScroolCheck() {


        Thread.sleep(5000)
        Espresso.onView(withId(R.id.charactersRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(14)); // Scrolls to the third item (position starts from 0)

        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withText("Name: Aemon Targaryen"))
            .check(matches(isDisplayed()))

        Espresso.onView(withId(R.id.charactersRecyclerView))
            .check(matches(hasDescendant(withId(R.id.bookNameTextView))))

    }

    @Test
    @UiThread
    fun CharacterListItemVisibiltyCheck() {

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
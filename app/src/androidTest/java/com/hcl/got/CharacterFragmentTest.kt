package com.hcl.got

import androidx.annotation.UiThread
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
        ActivityScenario.launch(MainActivity::class.java)
            .onActivity { it.updateCharsView("Test",
                listOf("https://www.anapioficeandfire.com/api/characters/2")) }
    }

    @Test
    @UiThread
    fun homeViewCheck() {

        // onView(isRoot()).perform(waitFor(5000))
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        Thread.sleep(5000)

        Espresso.onView(ViewMatchers.withId(R.id.bookNameTextView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.charactersTextView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.charactersRecyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}
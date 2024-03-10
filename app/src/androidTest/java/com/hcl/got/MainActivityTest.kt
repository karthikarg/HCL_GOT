package com.hcl.got

import androidx.annotation.UiThread
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hcl.got.ui.activity.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testUpdateCharsView() {
        val bookName = "A Game of Thrones"
        val characters = listOf(
            "https://www.anapioficeandfire.com/api/characters/1",
            "https://www.anapioficeandfire.com/api/characters/12"
        )

        activityRule.scenario.onActivity { activity ->
            activity.updateCharsView(bookName, characters)
        }


        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.bookNameTV))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("Book : $bookName"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    @UiThread
    fun leftDrawerVisibilityCheck() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Espresso.onView(ViewMatchers.withContentDescription(appContext.getString(R.string.navigation_drawer_open)))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withText("A Game of Thrones"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))


    }

}
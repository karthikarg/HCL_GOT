package com.hcl.got

import android.view.View
import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.Description
import org.junit.runner.RunWith
import java.util.regex.Matcher


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    @UiThread
    fun toolBarClickCheck() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withContentDescription(appContext.getString(R.string.navigation_drawer_open))).perform(click());

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

    }
    @Test
    @UiThread
    fun CheckLeftListItemCheck() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withContentDescription(appContext.getString(R.string.navigation_drawer_open))).perform(click());

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        onView(withText("A Game of Thrones")).check(matches(isDisplayed()))


    }

    @Test
    @UiThread
    fun homeViewCheck() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

       Thread.sleep(60000)

       /* onView(withId(R.id.bookNameTextView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.charactersTextView))
            .check(matches(isDisplayed()))*/

        onView(withId(R.id.charactersRecyclerView))
            .check(matches(isDisplayed()))

    }



}
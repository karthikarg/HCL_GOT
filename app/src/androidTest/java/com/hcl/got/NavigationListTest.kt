package com.hcl.got

import androidx.annotation.UiThread
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hcl.got.ui.activity.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NavigationListTest {

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
    fun CheckLeftListLastItemCheck() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        onView(withContentDescription(appContext.getString(R.string.navigation_drawer_open))).perform(click())

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(swipeDown())

        onView(withText("The Rogue Prince")).check(matches(isDisplayed()))

    }


    @Test
    @UiThread
    fun checkLeftListItemCountCheck() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        onView(withContentDescription(appContext.getString(R.string.navigation_drawer_open))).perform(click());

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(swipeDown())


        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(10)))


        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
    }


    @Test
    @UiThread
    fun checkLeftListItemClickCheck() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        onView(withContentDescription(appContext.getString(R.string.navigation_drawer_open))).perform(click());

        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.recyclerView)).perform(swipeDown())

        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
    }


}
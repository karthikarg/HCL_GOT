package com.hcl.got

import androidx.annotation.UiThread
import androidx.fragment.app.FragmentTransaction
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.hcl.got.ui.home.CharactersFragment
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
    fun homeViewCheck() {

       // onView(isRoot()).perform(waitFor(5000))
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

       Thread.sleep(5000)

        onView(withId(R.id.bookNameTextView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.charactersTextView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.charactersRecyclerView))
            .check(matches(isDisplayed()))

    }

    private fun startCharactersFragment(): CharactersFragment? {
        val activity: MainActivity = activityRule as MainActivity
        val transaction: FragmentTransaction =
            activity.supportFragmentManager.beginTransaction()
        val charactersFragment = CharactersFragment.newInstance("Test",
            listOf("https://www.anapioficeandfire.com/api/characters/2"))
        transaction.add(charactersFragment, "charactersFragment")
        transaction.commit()
        return charactersFragment
    }


}
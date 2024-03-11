package com.hcl.got

import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hcl.got.ui.utils.BindingUtils
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BindingUtilsTest {
    // Test case for setVisibility() method when the value is not empty
    @Test
    fun testSetVisibility_WhenValueNotEmpty() {
        // Set up the test environment
        val view = View(ApplicationProvider.getApplicationContext())

        // Call the setVisibility() method with a non-empty string
        BindingUtils.setVisibility(view, "Some non-empty string")

        // Verify that the visibility of the view is set to VISIBLE
        assertEquals(View.VISIBLE, view.visibility)
    }

    // Test case for setVisibility() method when the value is empty
    @Test
    fun testSetVisibility_WhenValueEmpty() {
        // Set up the test environment
        val view = View(ApplicationProvider.getApplicationContext())

        // Call the setVisibility() method with an empty string
        BindingUtils.setVisibility(view, "")

        // Verify that the visibility of the view is set to GONE
        assertEquals(View.GONE, view.visibility)
    }
}
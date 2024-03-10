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

    @Test
    fun testSetVisibility_WhenValueNotEmpty() {
        // Arrange
        val view = View(ApplicationProvider.getApplicationContext())

        // Act
        BindingUtils.setVisibility(view, "Some non-empty string")

        // Assert
        assertEquals(View.VISIBLE, view.visibility)
    }

    @Test
    fun testSetVisibility_WhenValueEmpty() {
        // Arrange
        val view = View(ApplicationProvider.getApplicationContext())

        // Act
        BindingUtils.setVisibility(view, "")

        // Assert
        assertEquals(View.GONE, view.visibility)
    }
}
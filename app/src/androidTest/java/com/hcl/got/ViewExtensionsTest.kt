package com.hcl.got

import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hcl.got.ui.utils.gone
import com.hcl.got.ui.utils.visible
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class ViewExtensionsTest {
    /*Test for the visible() extension function*/
    @Test
    fun testViewVisible() {
        // Create a mocked instance of View
        val view = mock(View::class.java)
        // Call the visible() extension function
        view.visible()
        // Verify that the visibility of the view is set to VISIBLE
        verify(view).visibility = View.VISIBLE
    }

    @Test
    fun testViewGone() {
        // Create a mocked instance of View
        val view = mock(View::class.java)
        // Call the gone() extension function
        view.gone()
        // Verify that the visibility of the view is set to GONE
        verify(view).visibility = View.GONE
    }
}
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
    @Test
    fun testViewVisible() {

        val view = mock(View::class.java)

        view.visible()

        verify(view).visibility = View.VISIBLE
    }

    @Test
    fun testViewGone() {

        val view = mock(View::class.java)

        view.gone()

        verify(view).visibility = View.GONE
    }
}
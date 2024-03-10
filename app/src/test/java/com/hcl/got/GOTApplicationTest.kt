package com.hcl.got

import android.content.Context
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GOTApplicationTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = mock(Context::class.java)
        // Mocking the application context
        GOTApplication.instance = mock(GOTApplication::class.java)
        `when`(GOTApplication.instance!!.applicationContext).thenReturn(context)
    }

    @Test
    fun testApplicationContext() {
        val applicationContext = GOTApplication.applicationContext()

        assertNotNull(applicationContext)
        // Verify that the applicationContext() returns the mocked context
        assert(applicationContext === context)
    }

}
package com.hcl.got

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.hcl.got.di.GOTApplicationModule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GOTApplicationModuleTest {
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var networkCapabilities: NetworkCapabilities

    @Mock
    private lateinit var network: Network

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testProvideBaseUrl() {
        val module = GOTApplicationModule()
        val baseUrl = module.provideBaseUrl()
        assertEquals("https://www.anapioficeandfire.com/api/", baseUrl)
    }

    @Test
    fun testHasNetwork_withNetworkConnected_shouldReturnTrue() {
        val connectivityManager = mock(ConnectivityManager::class.java)
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)

        val module = GOTApplicationModule()
        assertTrue(module.hasNetwork(context))
    }

    @Test
    fun testHasNetwork_withNoNetworkConnected_shouldReturnFalse() {
        val connectivityManager = mock(ConnectivityManager::class.java)
        `when`(connectivityManager.activeNetwork).thenReturn(null)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)

        val module = GOTApplicationModule()
        assertFalse(module.hasNetwork(context))
    }
}
package com.hcl.got.utils

import com.hcl.got.utils.Resource
import com.hcl.got.utils.Status
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class ResourceTest {


    @Test
    fun testSuccessResource() {

        val testData = "Book data"
        val resource = Resource.success(testData)
        assertEquals(Status.SUCCESS, resource.status)
        assertEquals(testData, resource.data)
        assertNull(resource.message)
    }

    @Test
    fun testErrorResource() {

        val testData: String? = null
        val errorMessage = "Something went wrong"


        val resource = Resource.error(testData, errorMessage)


        assertEquals(Status.ERROR, resource.status)
        assertEquals(testData, resource.data)
        assertEquals(errorMessage, resource.message)
    }

    @Test
    fun testLoadingResource() {

        val testData: String? = null


        val resource = Resource.loading(testData)


        assertEquals(Status.LOADING, resource.status)
        assertEquals(testData, resource.data)
        assertNull(resource.message)
    }
}
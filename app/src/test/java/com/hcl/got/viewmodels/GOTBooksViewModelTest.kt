package com.hcl.got.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hcl.got.MockResponseFileReader
import com.hcl.got.data.api.GOTApiService
import com.hcl.got.data.model.BooksData
import com.hcl.got.repos.GOTRepository
import com.hcl.got.ui.activity.viewmodels.GOTBooksViewModel
import com.hcl.got.utils.Status
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response


class GOTBooksViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private lateinit var viewModel: GOTBooksViewModel
    private lateinit var api: GOTApiService
    private lateinit var repository: GOTRepository

    private lateinit var mockedResponse : String
    private lateinit var mockResponseList : List<BooksData>

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        api = mock(GOTApiService::class.java)
        repository = GOTRepository(api)
        viewModel = GOTBooksViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined) // You can use other Dispatchers if needed

        mockedResponse = MockResponseFileReader("api/book.json").content
        mockResponseList = gson.fromJson(mockedResponse,object : TypeToken<List<BooksData>>() {}.type)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testBookApiDataSuccess() = runBlockingTest {

        `when`(api.getBooks()).thenReturn(Response.success(mockResponseList))

        // Fetch Book data
        viewModel.getGotBooks()

        viewModel.booksLiveData.observeForever {
            assertEquals(10, it.data?.size)
        }

        viewModel.booksLiveData.observeForever {
            assertEquals(Status.SUCCESS, it.status)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testBookApiDataFailure() = runBlockingTest {

        val mockCall: Call<String> = mock()

        `when`(api.getBooks()).thenReturn(Response.error(404,"null".toResponseBody()))

        // Fetch Book data
        viewModel.getGotBooks()

        viewModel.errorMessageData.observeForever {
            assertEquals("Something went wrong", it.message)
        }

        viewModel.errorMessageData.observeForever {
            assertEquals(Status.ERROR, it.status)
        }
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
package com.hcl.got.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hcl.got.MockResponseFileReader
import com.hcl.got.data.api.GOTApiService
import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import com.hcl.got.repos.GOTRepository
import com.hcl.got.ui.characters.CharactersViewModel
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


class GOTChractersViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private lateinit var viewModel: CharactersViewModel
    private lateinit var repository: GOTRepository
    private lateinit var api: GOTApiService

    private lateinit var mockedResponse : String
    private lateinit var mockResponseData : CharactersData

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        api = mock(GOTApiService::class.java)
        repository = GOTRepository(api)
        viewModel = CharactersViewModel(repository)
        Dispatchers.setMain(Dispatchers.Unconfined) // You can use other Dispatchers if needed

        mockedResponse = MockResponseFileReader("api/characters.json").content
        mockResponseData = gson.fromJson(mockedResponse,object : TypeToken<CharactersData>() {}.type)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testCharactersListSize() = runBlockingTest {

        // Set Characters data
        viewModel.setUrlList(listOf("2"))

        assertEquals(1, viewModel.getUrlList().size)
        assertEquals("2", viewModel.getUrlList()[0])

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testCharactersApiDataSuccess() = runBlockingTest {

        `when`(api.getCharacters(2)).thenReturn(Response.success(mockResponseData))

        // Fetch Characters data
        viewModel.getCharacters(listOf("2"))

        viewModel.charactersDataLiveData.observeForever {
            assertEquals(1, it.data?.size)
        }

        viewModel.charactersDataLiveData.observeForever {
            assertEquals(Status.SUCCESS, it.status)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testCharactersApiDataFailure() = runBlockingTest {


        `when`(api.getCharacters(2)).thenReturn(Response.error(404,"null".toResponseBody()))

        // Fetch Character data
        viewModel.getCharacters(listOf("2"))


        viewModel.charactersDataLiveData.observeForever {
            assertEquals(0, it.data?.size)
        }

        viewModel.charactersDataLiveData.observeForever {
            assertEquals(Status.SUCCESS, it.status)
        }
    }



    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
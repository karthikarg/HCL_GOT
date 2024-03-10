package com.hcl.got.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hcl.got.MockResponseFileReader
import com.hcl.got.data.api.GOTApiService
import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import com.hcl.got.repos.GOTRepository
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class BooksApiTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val server = MockWebServer()
    private lateinit var repository: GOTRepository
    private lateinit var mockedResponse: String
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    @Before
    fun init() {
        server.start(8000)
        var BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(GOTApiService::class.java)
        repository = GOTRepository(service)
    }

    @Test
    fun testBooksApiSuccess() {
        mockedResponse = MockResponseFileReader("api/book.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.gotApiService.getBooks() }
        val mockResponseList = gson.fromJson<List<BooksData>>(mockedResponse,object : TypeToken<List<BooksData>>() {}.type)

         val expectedResponseList =  response.body() ?: emptyList()

        Assert.assertNotNull(response)
        Assert.assertTrue(mockResponseList.size == expectedResponseList.size)
        Assert.assertTrue(mockResponseList[0].name == expectedResponseList[0].name)
    }

    @Test
    fun testCharactersDetailsApiSuccess() {
        mockedResponse = MockResponseFileReader("api/characters.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { repository.gotApiService.getCharacters(2) }
        val resultResponse = gson.fromJson<CharactersData>(mockedResponse,object : TypeToken<CharactersData>() {}.type)
        val expectedCharacter  =  response.body()

        Assert.assertNotNull(response)
        Assert.assertTrue(resultResponse.name == expectedCharacter?.name)
        Assert.assertTrue(resultResponse.gender == expectedCharacter?.gender)
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}
package com.hcl.got.data.api

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


/**
 * Service interface for making Game of Thrones API requests.
 */
interface GOTApiService {

    /**
     * Fetches the list of books from the API.
     * @return A response containing the list of books.
     */
    @GET("books")
    suspend fun getBooks(): Response<List<BooksData>>

    /**
     * Fetches details of a character based on the character ID.
     * @param id The ID of the character.
     * @return A response containing the details of the character.
     */
    @GET("characters/{id}")
    suspend fun getCharacters(@Path("id") id: Int
    ): Response<CharactersData>
}


package com.hcl.got.data.api

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import retrofit2.Response

/**
 * Helper interface for making Game of Thrones API requests.
 */
interface GOTApiHelper {

    /**
     * Fetches the list of books from the API.
     * @return A response containing the list of books.
     */
    suspend fun getBooks(): Response<List<BooksData>>

    /**
     * Fetches details of a character based on the character ID.
     * @param id The ID of the character.
     * @return A response containing the details of the character.
     */
    suspend fun getCharacters(id : Int): Response<CharactersData>

}
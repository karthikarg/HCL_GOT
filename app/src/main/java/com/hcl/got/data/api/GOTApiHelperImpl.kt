package com.hcl.got.data.api

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of the Game of Thrones API helper interface.
 * @param apiService The service interface for making API requests.
 */
class GOTApiHelperImpl  @Inject constructor(private val apiService: GOTApiService) : GOTApiHelper {

    /**
     * Fetches the list of books from the API.
     * @return A response containing the list of books.
     */
    override suspend fun getBooks(): Response<List<BooksData>> {
        return apiService.getBooks()
    }

    /**
     * Fetches details of a character based on the character ID.
     * @param id The ID of the character.
     * @return A response containing the details of the character.
     */
    override suspend fun getCharacters(id : Int): Response<CharactersData> {
        return apiService.getCharacters(id = id)
    }

}
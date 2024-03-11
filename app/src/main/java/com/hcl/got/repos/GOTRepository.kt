package com.hcl.got.repos

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import com.hcl.got.data.api.GOTApiService
import retrofit2.Response
import javax.inject.Inject

/**
 * Repository class responsible for fetching data related to Game of Thrones from the API service.
 * @param gotApiService The API service used to fetch data from the network.
 */
class GOTRepository @Inject constructor(val gotApiService: GOTApiService) {

    /**
     * Fetches the list of books from the API service.
     * @return A response containing the list of books.
     */
    suspend fun getBooks() : Response<List<BooksData>>
    {
        return gotApiService.getBooks()
    }

    /**
     * Fetches details of a character using character ID from the API service.
     * @param charId The ID of the character to fetch details for.
     * @return A response containing the details of the character.
     */
    suspend fun getChars(charId : Int) : Response<CharactersData>
    {
        return gotApiService.getCharacters(id=charId)
    }
}
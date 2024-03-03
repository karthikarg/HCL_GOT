package com.hcl.got.repos

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import com.hcl.got.data.api.GOTApiService
import retrofit2.Response
import javax.inject.Inject

class GOTRepository @Inject constructor(val gotApiService: GOTApiService) {

    suspend fun getBooks() : Response<List<BooksData>>
    {
        return gotApiService.getBooks()
    }

    suspend fun getChars(charId : Int) : Response<CharactersData>
    {
        return gotApiService.getCharacters(id=charId)
    }
}
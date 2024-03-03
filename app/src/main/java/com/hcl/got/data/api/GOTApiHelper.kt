package com.hcl.got.data.api

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import retrofit2.Response

interface GOTApiHelper {

    suspend fun getBooks(): Response<List<BooksData>>

    suspend fun getCharacters(id : Int): Response<CharactersData>

}
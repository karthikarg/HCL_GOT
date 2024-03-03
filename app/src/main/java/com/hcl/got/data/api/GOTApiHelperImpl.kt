package com.hcl.got.data.api

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import retrofit2.Response
import javax.inject.Inject

class GOTApiHelperImpl  @Inject constructor(private val apiService: GOTApiService) : GOTApiHelper {

    //  override suspend fun getUsers(): Response<List<User>> = apiService.getUsers()
    override suspend fun getBooks(): Response<List<BooksData>> {
        return apiService.getBooks()
    }

    override suspend fun getCharacters(id : Int): Response<CharactersData> {
        return apiService.getCharacters(id = id)
    }

}
package com.hcl.got.data.api

import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface GOTApiService {

    @GET("books")
    suspend fun getBooks(): Response<List<BooksData>>

    @GET("characters/{id}")
    suspend fun getCharacters(@Path("id") id: Int
    ): Response<CharactersData>
}


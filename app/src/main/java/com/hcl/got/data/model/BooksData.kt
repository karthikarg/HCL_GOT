package com.hcl.got.data.model

data class BooksData(
    val name: String,
    val isbn: String = "",
    val authors: List<String> = emptyList(),
    val characters: List<String> = emptyList()
)

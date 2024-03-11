package com.hcl.got.data.model

/**
 * Data class representing the details of a book.
 * @param name The name of the book.
 * @param isbn The ISBN of the book.
 * @param authors The authors of the book.
 * @param characters The characters appearing in the book.
 */
data class BooksData(
    val name: String,
    val isbn: String = "",
    val authors: List<String> = emptyList(),
    val characters: List<String> = emptyList()
)

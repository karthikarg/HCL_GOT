package com.hcl.got.data.model

data class CharactersData(
    val name: String,
    val gender: String,
    val titles: List<String>,
    val aliases: List<String>,
    val playedBy: List<String>
)
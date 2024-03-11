package com.hcl.got.data.model

/**
 * Data class representing character data.
 * @param url The URL of the character.
 * @param name The name of the character.
 * @param gender The gender of the character.
 * @param titles The list of titles held by the character.
 * @param aliases The list of aliases used by the character.
 * @param playedBy The list of actors who played the character.
 */
data class CharactersData(
    val url: String,
    val name: String,
    val gender: String,
    val titles: List<String>,
    val aliases: List<String>,
    val playedBy: List<String>
)
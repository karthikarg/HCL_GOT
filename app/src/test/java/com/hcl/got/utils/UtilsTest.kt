package com.hcl.got.utils

import com.hcl.got.utils.getCharacterId
import com.hcl.got.utils.getCharacterIdList
import com.hcl.got.utils.getListFirstItem
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UtilsTest {


    @Test
    fun checkingFirstItemFromList() {
        val list = listOf("Test A"," Test B")
        val firstItem = getListFirstItem(list)
        val expected = "Test A"
        assertEquals(expected, firstItem)
    }


    @Test
    fun checkingFirstItemFromEmptyList() {
        val list = emptyList<String>()
        val firstItem = getListFirstItem(list)
        val expected = ""
        assertEquals(expected, firstItem)
    }

    @Test
    fun checkingFirstItemFromNullList() {
        val list = null
        val firstItem = getListFirstItem(list)
        val expected = ""
        assertEquals(expected, firstItem)
    }


    @Test
    fun checkingCharacterId() {
        val url = "https://www.anapioficeandfire.com/api/characters/2"
        val firstItem = getCharacterId(url)
        val expected = 2
        assertEquals(expected, firstItem)
    }


    @Test
    fun checkingCharacterIdList() {
        val list = listOf(
            "https://www.anapioficeandfire.com/api/characters/2",
            "https://www.anapioficeandfire.com/api/characters/12")
        val characterIdList = getCharacterIdList(list)
        val expected = listOf(2, 12)
        assertEquals(expected, characterIdList)
    }

}
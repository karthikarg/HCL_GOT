package com.hcl.got.utils




/**Get the first item from list
 * @param list
 * */
fun getListFirstItem(list : List<String>?) : String{
    if(list.isNullOrEmpty())
        return ""

    return list[0]
}

/**Get the id from url
 * @param url
 * @return character id*/
fun getCharacterId(url : String) : Int
{
    val urlArray = url.split("/")
     val charId = urlArray[urlArray.size-1]
    return charId.toInt()
}


/**Get the id from url list
 * @param urlList
 * @return character id*/
fun getCharacterIdList(urlList : List<String>) : List<Int>
{
    val multipleIds = mutableListOf<Int>()

    urlList.forEach{
        multipleIds.add(getCharacterId(it))
    }

    return multipleIds
}
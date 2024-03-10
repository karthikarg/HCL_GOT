package com.hcl.got.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.got.data.model.CharactersData
import com.hcl.got.repos.GOTRepository
import com.hcl.got.utils.getCharacterIdList
import com.hcl.got.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(val gotRepository: GOTRepository) : ViewModel() {

    private var charactersUrlList = emptyList<String>()
    private val _charactersDataLiveData = MutableLiveData<Resource<List<CharactersData>>>()
    val charactersDataLiveData: LiveData<Resource<List<CharactersData>>> = _charactersDataLiveData


    fun setUrlList(list : List<String>){
        charactersUrlList = list

    }

    fun getUrlList() : List<String> {
        return charactersUrlList
    }


    /**Get the character details from the url for list of character ids
     * @param list**/
    fun getCharacters(list: List<String>){

        val multipleIds = getCharacterIdList(list)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val content = mutableListOf<CharactersData>()

                val runningTasks = multipleIds.map { id ->
                    async { // this will allow us to run multiple tasks in parallel
                        val apiResponse = gotRepository.getChars(id)
                        id to apiResponse // associate id and response for later
                    }
                }

                val responses = runningTasks.awaitAll()

                responses.forEach { (id, response) ->
                    if (response.isSuccessful) {
                        response.body()?.let {
                            content.add(it)
                        }
                    }
                }

                _charactersDataLiveData.postValue(Resource.success(content))
            }
        }
    }
}
package com.hcl.got.ui.activity.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.got.data.model.BooksData
import com.hcl.got.repos.GOTRepository
import com.hcl.got.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GOTBooksViewModel @Inject constructor (val gotRepository: GOTRepository) : ViewModel() {

    private val booksdata =  MutableLiveData<Resource<List<BooksData>>>()
     val booksLiveData : LiveData<Resource<List<BooksData>>>
        get() = booksdata

    private val errorMessage =  MutableLiveData<Resource<String>>()
    val errorMessageData : LiveData<Resource<String>>
        get() = errorMessage


    fun getGotBooks(){
        viewModelScope.launch {
            gotRepository.getBooks().let {

                if(it.isSuccessful){
                    it.body()?.let {list->
                        booksdata.value = Resource.success(list)
                    }
                }else
                {
                    errorMessage.value =  Resource.error(data = null, message = "Something went wrong")
                }
            }
        }

    }
}
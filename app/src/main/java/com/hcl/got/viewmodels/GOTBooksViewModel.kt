package com.hcl.got.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.got.data.model.BooksData
import com.hcl.got.repos.GOTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GOTBooksViewModel @Inject constructor (val gotRepository: GOTRepository) : ViewModel() {

    private val booksdata =  MutableLiveData<List<BooksData>>()
     val booksLiveData : LiveData<List<BooksData>>
        get() = booksdata

    fun getGotBooks(){
        viewModelScope.launch {
            gotRepository.getBooks().let {

                if(it.isSuccessful){
                    booksdata.value = it.body()
                }
            }
        }

    }
}
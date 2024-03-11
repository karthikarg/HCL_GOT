package com.hcl.got.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hcl.got.data.model.BooksData
import com.hcl.got.repos.GOTRepository
import com.hcl.got.utils.AppConstants.ERROR_MSG
import com.hcl.got.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class responsible for providing data related to Game of Thrones books to the UI.
 * This class is annotated with @HiltViewModel to allow for dependency injection using Hilt.
 * @param gotRepository The repository responsible for fetching data related to Game of Thrones books.
 */
@HiltViewModel
class GOTBooksViewModel @Inject constructor (val gotRepository: GOTRepository) : ViewModel() {

    private val booksdata =  MutableLiveData<Resource<List<BooksData>>>()
     val booksLiveData : LiveData<Resource<List<BooksData>>>
        get() = booksdata

    private val errorMessage =  MutableLiveData<Resource<String>>()
    val errorMessageData : LiveData<Resource<String>>
        get() = errorMessage


    /**
     * Fetches a list of Game of Thrones books asynchronously from the repository and updates LiveData accordingly.
     */
    fun getGotBooks(){
        viewModelScope.launch {
            // Calls the repository to get the list of books asynchronously
            gotRepository.getBooks().let {
                // Checks if the response is successful
                if(it.isSuccessful){
                    // Parses the response body and updates the LiveData with the successful result
                    it.body()?.let {list->
                        booksdata.value = Resource.success(list)
                    }
                }else
                {
                    // If the response is not successful, handles the error
                    errorMessage.value =  Resource.error(data = null, message = ERROR_MSG)
                }
            }
        }

    }
}
package com.hcl.got.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hcl.got.data.model.CharactersData
import com.hcl.got.databinding.FragmentHomeBinding
import com.hcl.got.ui.adapters.CharactersAdapter
import com.hcl.got.ui.utils.gone
import com.hcl.got.ui.utils.visible
import com.hcl.got.ui.viewmodels.CharactersViewModel
import com.hcl.got.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharactersFragment : Fragment() {

    /**
     * Companion object for creating instances of CharactersFragment with arguments.
     */
    companion object {
        // Keys for storing data in arguments Bundle
        const val KEY_CHARACTERS_LIST = "characters_list"
        const val KEY_BOOK_NAME = "selected_book_name"
        /**
         * Creates a new instance of CharactersFragment with provided book name and character list.
         * @param bookName The name of the selected book.
         * @param charList The list of characters.
         * @return A new instance of CharactersFragment with arguments set.
         */
        fun newInstance(bookName: String, charList: List<String>): CharactersFragment {
            val fragment = CharactersFragment()
            val args = Bundle()
            args.putStringArrayList(KEY_CHARACTERS_LIST, ArrayList(charList))
            args.putString(KEY_BOOK_NAME, bookName)
            fragment.arguments = args
            return fragment
        }
    }

    private val PAGE_LIMIT = 15

    private lateinit var charactersAdapter: CharactersAdapter
    var characterList: MutableList<CharactersData> = mutableListOf()

    private var _binding: FragmentHomeBinding? = null

    private val homeViewModel: CharactersViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var isLastPage = false
    var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /**Getting characters url list from bundle*/
        arguments?.getStringArrayList(KEY_CHARACTERS_LIST)?.let {
            homeViewModel.setUrlList(it)
        }

        /**Getting Book name from bundle*/
        binding.bookNameTV.text = "Book : " + arguments?.getString(KEY_BOOK_NAME)

        homeViewModel.charactersDataLiveData.observe(viewLifecycleOwner) {

            when(it.status){
                Status.SUCCESS ->  {
                    binding.progressBar.gone()
                    isLoading = false
                    it.data?.let { list->
                        characterList.addAll(list)
                        charactersAdapter.setListItems(list)
                        isLastPage = characterList.size == homeViewModel.getUrlList().size
                    }
                }
                Status.ERROR -> {}
                Status.LOADING -> {}
            }

        }

        // Set up the adapter for the RecyclerView
        setAdapter()
        // Fetch additional items if needed
        fetchItems()


        // Add an OnScrollListener to the charactersRecyclerView
        binding.charactersRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // Check if the recyclerView cannot be scrolled vertically in the positive direction
                if (!recyclerView.canScrollVertically(1)) {
                    // If not, call the fetchItems() function to fetch more items
                    fetchItems()
                }
            }
        })

        return root
    }

    /**
     * Sets up the adapter for the charactersRecyclerView.
     * Creates a new instance of CharactersAdapter and assigns it to charactersAdapter.
     * Sets the charactersRecyclerView's adapter to the newly created adapter.
     */
    private fun setAdapter() {
        // Create a new instance of CharactersAdapter
        charactersAdapter = CharactersAdapter()
        // Assign the created adapter to the charactersRecyclerView
        binding.charactersRecyclerView.adapter = charactersAdapter

    }

    /**
     * Fetches additional items if not currently loading and not reached the last page.
     * Also updates the loading state and requests characters from the view model.
     */
    private fun fetchItems() {
        // Check if not currently loading and not reached the last page
        if (!isLoading && !isLastPage) {
            // Show the progress bar
            binding.progressBar.visible()
            // Calculate the last index to fetch from the URL list
            val last = if (homeViewModel.getUrlList().size < characterList.size + PAGE_LIMIT)
                homeViewModel.getUrlList().size
            else
                characterList.size + PAGE_LIMIT
            // Extract sublist from URL list based on current characterList size and PAGE_LIMIT
            val list = homeViewModel.getUrlList().subList(characterList.size, last)
            // Request characters from the view model
            homeViewModel.getCharacters(list)
            // Update loading state to true
            isLoading = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
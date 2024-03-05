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
import com.hcl.got.ui.utils.gone
import com.hcl.got.ui.utils.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharactersFragment : Fragment() {

    companion object {
        const val KEY_CHARACTERS_LIST = "characters_list"
        const val KEY_BOOK_NAME = "selected_book_name"
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
            binding.progressBar.gone()
            isLoading = false
            it?.let {
                characterList.addAll(it)
                charactersAdapter.setListItems(it)
                isLastPage = characterList.size == homeViewModel.getUrlList().size
            }
        }

        setAdapter()
        fetchItems()


        // Implement load more
        binding.charactersRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    fetchItems()
                }
            }
        })

        return root
    }

    /**Setting characters adapter*/
    private fun setAdapter() {
        charactersAdapter = CharactersAdapter()
        binding.charactersRecyclerView.adapter = charactersAdapter

    }

    private fun fetchItems() {

        if (!isLoading && !isLastPage) {
            binding.progressBar.visible()
            val last = if (homeViewModel.getUrlList().size < characterList.size + PAGE_LIMIT)
                homeViewModel.getUrlList().size
            else
                characterList.size + PAGE_LIMIT

            val list = homeViewModel.getUrlList().subList(characterList.size, last)

            homeViewModel.getCharacters(list)
            isLoading = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
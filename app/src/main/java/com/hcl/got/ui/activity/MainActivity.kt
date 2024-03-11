package com.hcl.got.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hcl.got.R
import com.hcl.got.databinding.ActivityMainBinding
import com.hcl.got.ui.adapters.BooksAdapter
import com.hcl.got.ui.characters.CharactersFragment
import com.hcl.got.utils.Status
import com.hcl.got.ui.viewmodels.GOTBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bookAdapter: BooksAdapter

    lateinit var gotBooksViewModel: GOTBooksViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout and set content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize gotBooksViewModel
        gotBooksViewModel = ViewModelProvider(this)[GOTBooksViewModel::class.java]

        // Set up toolbar and navigation drawer
        setSupportActionBar(binding.appBarMain.toolbar)
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
            }
        }
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set up RecyclerView adapter
        bookAdapter = BooksAdapter()
        binding.recyclerView.adapter = bookAdapter

        // Fetch book data using ViewModel
        lifecycleScope.launch {
            gotBooksViewModel?.getGotBooks()
        }

        // Handle item click in Book RecyclerView
        bookAdapter.onItemClick = { bookDetails ->
            Toast.makeText(baseContext, bookDetails.name, Toast.LENGTH_SHORT).show()
            updateCharsView(bookDetails.name, bookDetails.characters)
            binding.drawerLayout.closeDrawers()
        }

        // Observe booksLiveData for updates
        gotBooksViewModel?.booksLiveData?.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        bookAdapter.setListItems(list)
                        if (list.isNullOrEmpty().not()) {
                            list[0]?.let { bookDetails ->
                                updateCharsView(bookDetails.name, bookDetails.characters)
                            }
                        }
                    }
                }

                Status.ERROR -> {
                    // Handle error status
                }

                Status.LOADING -> {
                    // Handle loading status
                }
            }

        }

        // Observe errorMessageData for errors
        gotBooksViewModel?.errorMessageData?.observe(this) {
            if (it.message.isNullOrEmpty().not()) {
                Toast.makeText(baseContext, it.message, Toast.LENGTH_SHORT).show()
            }
        }


    }


    /**
     * Update character view fragment.
     * @param bookName The name of the book.
     * @param list The list of characters.
     */
    fun updateCharsView(bookName: String, list: List<String>) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.charLayout, CharactersFragment.newInstance(bookName, list))
        transaction.commit()
    }

}
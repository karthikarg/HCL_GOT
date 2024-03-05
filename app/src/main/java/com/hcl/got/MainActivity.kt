package com.hcl.got

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hcl.got.databinding.ActivityMainBinding
import com.hcl.got.ui.adapters.BooksAdapter
import com.hcl.got.ui.characters.CharactersFragment
import com.hcl.got.viewmodels.GOTBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BooksAdapter

    // private var gotBooksViewModel : GOTBooksViewModel? = null
    private val gotBooksViewModel: GOTBooksViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        bookAdapter = BooksAdapter()
        binding.recyclerView.adapter = bookAdapter

        lifecycleScope.launch {
            gotBooksViewModel?.getGotBooks()
        }


        bookAdapter.onItemClick = { bookDetails ->
            Toast.makeText(baseContext, bookDetails.name, Toast.LENGTH_SHORT).show()
            updateCharsView(bookDetails.name, bookDetails.characters)
            binding.drawerLayout.closeDrawers()
        }

        gotBooksViewModel?.booksLiveData?.observe(this) {
            bookAdapter.setListItems(it)
            if (it.isNullOrEmpty().not())
            {
                it[0]?.let { bookDetails->
                    updateCharsView(bookDetails.name, bookDetails.characters)
                }
            }
        }


    }


    fun updateCharsView(bookName: String, list: List<String>) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.charLayout, CharactersFragment.newInstance(bookName, list))
        transaction.commit()
    }

}
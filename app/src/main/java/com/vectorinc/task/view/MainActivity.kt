package com.vectorinc.task.view

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vectorinc.task.R
import com.vectorinc.task.adapter.RecyclerviewAdapter
import com.vectorinc.task.databinding.ActivityMainBinding
import com.vectorinc.task.domain.model.Character
import com.vectorinc.task.viewmodel.CharacterState
import com.vectorinc.task.viewmodel.MainActivityEvents
import com.vectorinc.task.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerviewAdapter
    private lateinit var toolbar : Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        toolbar = binding.toolbar.toolbar
        setContentView(binding.root)
        setSupportActionBar(toolbar)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        initViewModel()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is CharacterState.Error -> TODO()
                        is CharacterState.Success -> {
                            showList(uiState.character, binding.recyclerView)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        super.onCreateOptionsMenu(menu)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        return true
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private fun showList(list: List<Character>, recyclerView: RecyclerView) {
        adapter = RecyclerviewAdapter(list,this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.onEvent(
            MainActivityEvents.OnSearchQueryChange(newText ?: "")
        )

        return true

    }
}
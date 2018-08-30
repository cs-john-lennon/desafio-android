package com.csjohnlennon.desafioandroid.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import com.csjohnlennon.desafioandroid.R
import com.csjohnlennon.desafioandroid.adapter.RepositoryAdapter
import com.csjohnlennon.desafioandroid.view.widget.EndlessRecyclerViewScrollListener
import com.csjohnlennon.desafioandroid.viewmodel.RepositoryViewModel
import kotlinx.android.synthetic.main.content_repositories.*

class RepositoriesActivity : AppCompatActivity() {

    private var adapter = RepositoryAdapter()
    private var layoutManager = LinearLayoutManager(this)
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private lateinit var viewModel: RepositoryViewModel

    companion object {
        private var PAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)
        viewModel.fetch(PAGE)
        viewModel.getRepositories().observe(this, Observer {
            it?.let {
                adapter.updateList(it.repositories) }
        })
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewRepositories)
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}

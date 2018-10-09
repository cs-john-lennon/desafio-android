package com.csjohnlennon.desafioandroid.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.csjohnlennon.desafioandroid.R
import com.csjohnlennon.desafioandroid.adapter.RepositoryAdapter
import com.csjohnlennon.desafioandroid.view.widget.EndlessRecyclerViewScrollListener
import com.csjohnlennon.desafioandroid.viewmodel.RepositoryViewModel
import kotlinx.android.synthetic.main.activity_repositories.repositories

class RepositoriesActivity : AppCompatActivity() {

    private lateinit var viewModel: RepositoryViewModel
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private val adapter = RepositoryAdapter()
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)
        setupScrollView()
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupScrollView() {
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.fetch()
            }
        }
    }

    private fun setupRecyclerView() {
        repositories.layoutManager = layoutManager
        repositories.adapter = adapter
        repositories.addOnScrollListener(scrollListener)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)
        viewModel.fetch()
        viewModel.getRepositories().observe(this, Observer {
            it?.repositories?.let { adapter.updateList(it) }
        })
    }
}

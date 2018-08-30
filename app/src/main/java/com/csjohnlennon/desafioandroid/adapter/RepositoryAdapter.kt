package com.csjohnlennon.desafioandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.csjohnlennon.desafioandroid.R
import com.csjohnlennon.desafioandroid.model.RepositoryResponse
import kotlinx.android.synthetic.main.repository_layout.view.*

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder>() {

    private val repositories = arrayListOf<RepositoryResponse>()

    fun updateList(items: List<RepositoryResponse>? = null) {
        repositories.addAll(items!!)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val values = repositories[position]
        holder.itemView.repositoryName.text = values.name
        holder.itemView.repositoryDescription.text = values.description
        holder.itemView.stargazersCount.text = values.stargazers_count.toString()
        holder.itemView.forksCount.text = values.forks.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        return RepositoryHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_layout, parent, false))
    }

    override fun getItemCount() = repositories.size

    class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repositoryName = view.repositoryName
        val repositoryDescription = view.repositoryDescription
        val stargazersCount = view.stargazersCount
        val forksCount = view.forksCount
    }
}
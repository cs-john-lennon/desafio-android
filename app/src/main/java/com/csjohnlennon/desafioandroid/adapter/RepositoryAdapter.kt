package com.csjohnlennon.desafioandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.csjohnlennon.desafioandroid.R
import com.csjohnlennon.desafioandroid.model.RepositoryResponse
import kotlinx.android.synthetic.main.repository_layout.view.avatar
import kotlinx.android.synthetic.main.repository_layout.view.forksCount
import kotlinx.android.synthetic.main.repository_layout.view.repositoryDescription
import kotlinx.android.synthetic.main.repository_layout.view.repositoryName
import kotlinx.android.synthetic.main.repository_layout.view.stargazersCount

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder>() {

    private var items = arrayListOf<RepositoryResponse>()

    fun updateList(repositoriesVO: List<RepositoryResponse>) {
        repositoriesVO?.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val repository = items[position]
        repository?.let {
            holder.itemView.repositoryName.text = it.name
            holder.itemView.repositoryDescription.text = it.description
            holder.itemView.stargazersCount.text = it.stargazersCount.toString()
            holder.itemView.forksCount.text = it.forks.toString()
            it.owner?.avatarUrl?.let { Glide.with(holder.itemView).load(it).into(holder.avatar) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        return RepositoryHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.repository_layout, parent, false))
    }

    override fun getItemCount() = items.size

    class RepositoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repositoryName: TextView = view.repositoryName
        val repositoryDescription: TextView = view.repositoryDescription
        val stargazersCount: TextView = view.stargazersCount
        val forksCount: TextView = view.forksCount
        val avatar: ImageView = view.avatar
    }
}
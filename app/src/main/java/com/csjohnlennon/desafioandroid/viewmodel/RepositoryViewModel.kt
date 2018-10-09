package com.csjohnlennon.desafioandroid.viewmodel

import android.arch.lifecycle.ViewModel
import com.csjohnlennon.desafioandroid.model.RepositoriesData

class RepositoryViewModel : ViewModel() {
    private var repositoryData = RepositoriesData()
    private var page = 1

    fun fetch() {
        repositoryData.fetch(page)
        repositoryData.getRepositories().value?.repositories?.size?.let {
            if (it > 0) page++
        }
    }

    fun getRepositories() = repositoryData.getRepositories()
}

package com.csjohnlennon.desafioandroid.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.csjohnlennon.desafioandroid.network.APIClient
import com.csjohnlennon.desafioandroid.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesData {

    private val service = APIClient.getClient().create(GithubService::class.java)
    private val repositoriesLiveData: MutableLiveData<RepositoriesVO> = MutableLiveData()

    fun fetch(position: Int = 0) {
        val callback = service.fetchRepositories(position)
        callback.enqueue(object : Callback<RepositoriesResponse> {
            override fun onResponse(call: Call<RepositoriesResponse>?, response: Response<RepositoriesResponse>?) {
                response?.isSuccessful?.let {
                    repositoriesLiveData.postValue(RepositoriesVO(repositories = response.body().items))
                }
            }

            override fun onFailure(call: Call<RepositoriesResponse>?, t: Throwable?) {
            }
        })
    }

    fun getRepositories(): LiveData<RepositoriesVO> = repositoriesLiveData
}
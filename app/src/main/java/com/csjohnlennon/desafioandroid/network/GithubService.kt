package com.csjohnlennon.desafioandroid.network

import com.csjohnlennon.desafioandroid.model.RepositoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("/search/repositories?q=language:Java&sort=stars")
    fun fetchRepositories(@Query("page") page: Int): Call<RepositoriesResponse>
}
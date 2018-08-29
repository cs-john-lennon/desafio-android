package com.csjohnlennon.desafioandroid.model

import com.google.gson.annotations.SerializedName

class RepositoryResponse(@SerializedName("name") var name: String,
    @SerializedName("description") var description: String,
    @SerializedName("forks") var forks: Long = 0,
    @SerializedName("stargazers_count") var stargazers_count: Long = 0,
    @SerializedName("owner") var owner: Owner? = null)

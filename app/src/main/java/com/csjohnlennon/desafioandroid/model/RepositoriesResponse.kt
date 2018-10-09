package com.csjohnlennon.desafioandroid.model

import com.google.gson.annotations.SerializedName

class RepositoriesResponse(@SerializedName("total_count") var totalCount: Long? = 0,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = false,
    @SerializedName("items") var items: List<RepositoryResponse>)
package com.csjohnlennon.desafioandroid.model

import com.google.gson.annotations.SerializedName

class PullResponse(@SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null,
    @SerializedName("created_at") var created_at: String? = null,
    @SerializedName("html_url") var html_url: String? = null,
    @SerializedName("user") var owner: Owner? = null)

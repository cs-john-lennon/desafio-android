package com.csjohnlennon.desafioandroid.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RepositoryResult {

    @SerializedName("total_count")
    public long totalCount;

    @SerializedName("incomplete_results")
    public boolean incompleteResults;

    @SerializedName("items")
    public List<Repository> items = new ArrayList<>();

}

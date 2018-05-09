package com.csjohnlennon.desafioandroid.network.model;

import com.google.gson.annotations.SerializedName;

public class Repository {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("forks")
    private long forks;

    @SerializedName("stargazers_count")
    private long stargazers_count;

    public Repository(String name, String description, long forks, long stargazers_count) {
        this.name = name;
        this.description = description;
        this.forks = forks;
        this.stargazers_count = stargazers_count;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getForks() {
        return forks;
    }

    public long getStargazersCount() {
        return stargazers_count;
    }

}

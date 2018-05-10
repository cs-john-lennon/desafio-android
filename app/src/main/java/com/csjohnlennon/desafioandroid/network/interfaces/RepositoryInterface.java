package com.csjohnlennon.desafioandroid.network.interfaces;

import com.csjohnlennon.desafioandroid.network.model.RepositoryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepositoryInterface {

    @GET("/search/repositories?q=language:Java&sort=stars")
    Call<RepositoryResult> search(@Query("page") int page);

}

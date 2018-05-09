package com.csjohnlennon.desafioandroid.network.interfaces;

import com.csjohnlennon.desafioandroid.network.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepositoryInterface {

    @GET("/search/repositories?q=language:Java&sort=stars&page=1")
    Call<List<Repository>> search(@Query("page") int page);

}

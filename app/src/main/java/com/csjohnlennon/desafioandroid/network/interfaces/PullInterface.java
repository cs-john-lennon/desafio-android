package com.csjohnlennon.desafioandroid.network.interfaces;

import com.csjohnlennon.desafioandroid.network.model.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PullInterface {

    @GET("/repos/{owner}/{repo}/pulls")
    Call<List<Pull>> repos(@Path("owner") String owner,
                            @Path("repo") String repo);

}

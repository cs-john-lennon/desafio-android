package com.csjohnlennon.desafioandroid.ui.presenter;

import com.csjohnlennon.desafioandroid.network.model.Repository;

import java.util.List;

import retrofit2.Callback;

public interface RepositoriesPresenter {

    public void search(int page, Callback<List<Repository>> callback);

}

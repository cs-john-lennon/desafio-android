package com.csjohnlennon.desafioandroid.ui.presenter;

import com.csjohnlennon.desafioandroid.network.APIClient;
import com.csjohnlennon.desafioandroid.network.interfaces.RepositoryInterface;
import com.csjohnlennon.desafioandroid.network.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class RepositoriesPresenterImpl implements RepositoriesPresenter {

    @Override
    public void search(int page, Callback<List<Repository>> callback) {

        RepositoryInterface repositoryInterface = APIClient.getClient().create(RepositoryInterface.class);
        Call<List<Repository>> call = repositoryInterface.search(page);

        call.enqueue(callback);
    }

}

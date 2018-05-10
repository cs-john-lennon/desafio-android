package com.csjohnlennon.desafioandroid.ui.presenter;

import com.csjohnlennon.desafioandroid.network.APIClient;
import com.csjohnlennon.desafioandroid.network.interfaces.RepositoryInterface;
import com.csjohnlennon.desafioandroid.network.model.RepositoryResult;
import com.csjohnlennon.desafioandroid.ui.activity.RepositorieView;
import com.csjohnlennon.desafioandroid.ui.activity.RepositoriesActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoriesPresenterImpl implements RepositoriesPresenter {

    private RepositorieView repositorieView;

    public void addView(RepositorieView repositorieView) {
        this.repositorieView = repositorieView;
    }

    public void removeView() {
        this.repositorieView = null;
    }

    @Override
    public void search(int page) {
        RepositoryInterface repositoryInterface = APIClient.getClient().create(RepositoryInterface.class);
        Call<RepositoryResult> call = repositoryInterface.search(page);

        call.enqueue(new Callback<RepositoryResult>() {
            @Override
            public void onResponse(Call<RepositoryResult> call, Response<RepositoryResult> response) {

                try {
                    RepositoryResult repositoryResult = response.body();
                    if (repositoryResult.items.size() > 0) {
                        repositorieView.populateRepositorie(repositoryResult.items);
                        RepositoriesActivity.PAGE++;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<RepositoryResult> call, Throwable t) {
                repositorieView.showResults();
                repositorieView.showError(t.getMessage());
            }
        });
    }

}
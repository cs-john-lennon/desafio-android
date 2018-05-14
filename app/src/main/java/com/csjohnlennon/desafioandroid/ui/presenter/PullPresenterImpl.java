package com.csjohnlennon.desafioandroid.ui.presenter;

import com.csjohnlennon.desafioandroid.network.APIClient;
import com.csjohnlennon.desafioandroid.network.interfaces.PullInterface;
import com.csjohnlennon.desafioandroid.network.model.Pull;
import com.csjohnlennon.desafioandroid.ui.activity.PullView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PullPresenterImpl implements PullPresenter {

    PullView pullView;

    public void addView(PullView pullView) {
        this.pullView = pullView;
    }

    public void removeView() {
        this.pullView = null;
    }

    @Override
    public void repos(String owner, String rep) {

        PullInterface pullInterface = APIClient.getClient().create(PullInterface.class);
        Call<List<Pull>> call       = pullInterface.repos(owner, rep);
        call.enqueue(new Callback<List<Pull>>() {
            @Override
            public void onResponse(Call<List<Pull>> call, Response<List<Pull>> response) {
                try {
                    List<Pull> pulls = response.body();
                    if(pulls.size() > 0) {
                        pullView.populate(pulls);
                    }
                } catch (NullPointerException e) {
                    pullView.toggleNoResultsInfo();
                }
            }

            @Override
            public void onFailure(Call<List<Pull>> call, Throwable t) {
                pullView.toggleLoader();
                pullView.toggleNoResultsInfo();
                pullView.showError(t.getMessage());
            }
        });
    }

}

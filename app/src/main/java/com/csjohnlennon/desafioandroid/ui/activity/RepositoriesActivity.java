package com.csjohnlennon.desafioandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.csjohnlennon.desafioandroid.R;
import com.csjohnlennon.desafioandroid.network.model.Repository;
import com.csjohnlennon.desafioandroid.ui.presenter.RepositoriesPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoriesActivity extends AppCompatActivity {

    @BindView
            (R.id.repositories_list_recycler_view)
    RecyclerView repositories_list_recycler_view;

    private RepositoriesPresenterImpl repositoriesPresenter;
    public static int PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        repositoriesPresenter = new RepositoriesPresenterImpl();
        loadPage(PAGE);
    }

    public void loadPage(int page) {
        repositoriesPresenter.search(page, new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                List<Repository> repositories = response.body();
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {

            }
        });
    }

}

package com.csjohnlennon.desafioandroid.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csjohnlennon.desafioandroid.R;
import com.csjohnlennon.desafioandroid.adapter.RepositoryAdapter;
import com.csjohnlennon.desafioandroid.network.model.Repository;
import com.csjohnlennon.desafioandroid.ui.presenter.RepositoriesPresenterImpl;
import com.csjohnlennon.desafioandroid.ui.widget.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesActivity extends AppCompatActivity implements RepositorieView {

    public static int PAGE = 1;
    public static String REPOSITORY_LIST = ".repositories";
    public static String REPOSITORY_LIST_POSITION = ".position";
    private List<Repository> repositories;

    @BindView(R.id.list_recycler_view)
    RecyclerView repositories_list_recycler_view;

    @BindView(R.id.noresults_text_view)
    TextView noresults_text_view;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private LinearLayoutManager layoutManager;
    private EndlessRecyclerViewScrollListener scrollListener;
    private RepositoryAdapter adapter;
    private RepositoriesPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        ButterKnife.bind(this);
        initialize();

        if (savedInstanceState != null) {
            PAGE = savedInstanceState.getInt("page");
            showResults();
            adapter.clear();
            adapter.add(savedInstanceState.<Repository>getParcelableArrayList(REPOSITORY_LIST));
            repositories_list_recycler_view.setVerticalScrollbarPosition(savedInstanceState.getInt(REPOSITORY_LIST_POSITION));
        } else {
            loadPage();
        }

    }

    public void initialize() {

        repositories = new ArrayList<>();
        presenter = new RepositoriesPresenterImpl();
        presenter.addView(this);
        adapter = new RepositoryAdapter(this, repositories);

        layoutManager = new LinearLayoutManager(this);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadPage();
            }
        };

        repositories_list_recycler_view.addOnScrollListener(scrollListener);
        repositories_list_recycler_view.setLayoutManager(layoutManager);
        repositories_list_recycler_view.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeView();
    }

    public void loadPage() {
        progressBar.setVisibility(View.VISIBLE);
        noresults_text_view.setVisibility(View.GONE);
        presenter.search(PAGE);
    }

    @Override
    public void showResults() {
        progressBar.setVisibility(View.GONE);
        noresults_text_view.setVisibility(View.GONE);
        repositories_list_recycler_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResults() {
        progressBar.setVisibility(View.GONE);
        repositories_list_recycler_view.setVisibility(View.GONE);
        noresults_text_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void toggleLoader() {
        switch (progressBar.getVisibility()) {
            case View.GONE:
                progressBar.setVisibility(View.VISIBLE);
                break;
            case View.VISIBLE:
                progressBar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void toggleNoResultsInfo() {
        switch (noresults_text_view.getVisibility()) {
            case View.GONE:
                noresults_text_view.setVisibility(View.VISIBLE);
                break;
            case View.VISIBLE:
                noresults_text_view.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void populateRepositorie(List<Repository> repositoryList) {
        repositories.addAll(repositoryList);
        adapter.add(repositoryList);
        int count = repositories_list_recycler_view.getAdapter().getItemCount();
        if(count > 0) {
            showResults();
        } else {
            hideResults();
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(REPOSITORY_LIST, (ArrayList<? extends Parcelable>) repositories);
        outState.putInt(REPOSITORY_LIST_POSITION, repositories_list_recycler_view.getVerticalScrollbarPosition());
        outState.putInt("page", PAGE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        PAGE = savedInstanceState.getInt("page");
        adapter.clear();
        adapter.add(savedInstanceState.<Repository>getParcelableArrayList(REPOSITORY_LIST));
        repositories_list_recycler_view.setVerticalScrollbarPosition(savedInstanceState.getInt(REPOSITORY_LIST_POSITION));
    }

}

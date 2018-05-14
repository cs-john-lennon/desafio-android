package com.csjohnlennon.desafioandroid.ui.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.csjohnlennon.desafioandroid.adapter.PullAdapter;
import com.csjohnlennon.desafioandroid.network.model.Owner;
import com.csjohnlennon.desafioandroid.network.model.Pull;
import com.csjohnlennon.desafioandroid.network.model.Repository;
import com.csjohnlennon.desafioandroid.ui.presenter.PullPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PullActivity extends AppCompatActivity implements PullView {

    public static String REPOSITORY_KEY = ".repository";
    public static String FORKS = ".forks";
    public static String OWNER_KEY = ".owner";
    public static String LIST_POSITION = "position";

    private Repository repository;
    private Owner owner;
    private List<Pull> pulls;

    @BindView(R.id.list_recycler_view)
    RecyclerView pulls_list_recycler_view;

    @BindView(R.id.noresults_text_view)
    TextView noresults_text_view;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private LinearLayoutManager layoutManager;
    private PullAdapter adapter;
    private PullPresenterImpl presenter;
    private PullAdapter.PullAdapterClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);
        ButterKnife.bind(this);
        initialize();

        if (repository != null && owner != null) {
            toggleNoResultsInfo();
            toggleLoader();
            load();
        }
    }

    public void initialize() {
        setTitle("");
        listener = new PullAdapter.PullAdapterClickListener() {
            @Override
            public void onClick(String url) {
                openInWebBrowser(url);
            }
        };
        repository = getIntent().getExtras().getParcelable(REPOSITORY_KEY);
        owner = getIntent().getExtras().getParcelable(OWNER_KEY);
        pulls = new ArrayList<>();
        presenter = new PullPresenterImpl();
        presenter.addView(this);
        adapter = new PullAdapter(this, pulls, listener);
        layoutManager = new LinearLayoutManager(this);
        pulls_list_recycler_view.setLayoutManager(layoutManager);
        pulls_list_recycler_view.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeView();
    }

    void load() {
        presenter.repos(owner.login, repository.name);
    }

    @Override
    public void populate(List<Pull> pullList) {
        pulls.addAll(pullList);
        adapter.add(pullList);
        int count = pulls_list_recycler_view.getAdapter().getItemCount();
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
    public void showResults() {
        progressBar.setVisibility(View.GONE);
        noresults_text_view.setVisibility(View.GONE);
        pulls_list_recycler_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResults() {
        progressBar.setVisibility(View.GONE);
        pulls_list_recycler_view.setVisibility(View.GONE);
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
    public void openInWebBrowser(String url) {
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FORKS, (ArrayList<? extends Parcelable>) pulls);
        outState.putInt(LIST_POSITION, pulls_list_recycler_view.getVerticalScrollbarPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.clear();
        adapter.add(savedInstanceState.<Pull>getParcelableArrayList(FORKS));
        pulls_list_recycler_view.setVerticalScrollbarPosition(savedInstanceState.getInt(LIST_POSITION));
    }
}

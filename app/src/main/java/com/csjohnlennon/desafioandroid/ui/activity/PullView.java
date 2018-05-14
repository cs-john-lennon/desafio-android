package com.csjohnlennon.desafioandroid.ui.activity;

import com.csjohnlennon.desafioandroid.network.model.Pull;

import java.util.List;

public interface PullView {

    void populate(List<Pull> pullList);

    void showError(String msg);

    void showResults();

    void hideResults();

    void toggleLoader();

    void toggleNoResultsInfo();

    void openInWebBrowser(String url);

}

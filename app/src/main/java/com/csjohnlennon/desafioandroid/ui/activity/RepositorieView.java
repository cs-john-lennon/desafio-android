package com.csjohnlennon.desafioandroid.ui.activity;

import com.csjohnlennon.desafioandroid.network.model.Repository;

import java.util.List;

public interface RepositorieView {

    void populateRepositorie(List<Repository> repositoryList);

    void showError(String msg);

    void showResults();

    void hideResults();

}

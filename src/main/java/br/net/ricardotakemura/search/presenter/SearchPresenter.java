package br.net.ricardotakemura.search.presenter;

import br.net.ricardotakemura.search.view.SearchView;

import java.io.File;

public interface SearchPresenter {
    void createIndexes(File dataDir);
    void loadIndexes();
    void search(String words);
    void setView(SearchView view);
}
package br.net.ricardotakemura.search.presenter.impl;

import java.io.File;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.model.impl.MapSearchModelImpl;
import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchView searchView;
    private SearchModel searchModel;

    public SearchPresenterImpl(SearchView searchView) {
        this.searchView = searchView;
        this.searchModel = new MapSearchModelImpl();
    }

    public void search(String words) {
        var result = searchModel.search(words.split(" "));
        if (searchView != null) {
            searchView.onSearchResult(result);
        }
    }

    public void createIndexes(File dataDir) {
        try {
            searchModel.createIndexes(dataDir);
            if (searchView != null) {
                searchView.onCreatedIndex();
            }
        } catch (Exception e) {
            if (searchView != null) {
                searchView.onFailedIndex(e);
            }
        }
    }

    public void loadIndexes() {
        try {
            searchModel.loadIndexes();
            if (searchView != null) {
                searchView.onLoadedIndex();
            }
        } catch (Exception e) {
            if (searchView != null) {
                searchView.onFailedIndex(e);
            }            
        }
    }
}

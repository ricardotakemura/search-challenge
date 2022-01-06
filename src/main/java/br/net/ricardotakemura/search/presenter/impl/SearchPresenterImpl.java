package br.net.ricardotakemura.search.presenter.impl;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.model.impl.TreeMapSearchModelImpl;
import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.view.SearchView;

public class SearchPresenterImpl implements SearchPresenter {

    private SearchView searchView;
    private SearchModel searchModel;

    public SearchPresenterImpl(SearchView searchView) {
        this.searchView = searchView;
        this.searchModel = new TreeMapSearchModelImpl();
    }

    public void search(String words) {
        var result = searchModel.search(words.split(" "));
        searchView.onReceived(result);
    }
}

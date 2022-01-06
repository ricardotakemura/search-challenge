package br.net.ricardotakemura.search.view.impl;

import java.io.File;
import java.util.Set;

import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.presenter.impl.SearchPresenterImpl;
import br.net.ricardotakemura.search.view.SearchView;

public class ConsoleSearchViewImpl implements SearchView {

    private String words;
    private SearchPresenter searchPresenter;

    public ConsoleSearchViewImpl() {
        searchPresenter = new SearchPresenterImpl(this);
    }

    public void start(String... args) {
        searchPresenter.search(words);
    }
    
    public void onReceived(Set<File> files) {
        
    }
}

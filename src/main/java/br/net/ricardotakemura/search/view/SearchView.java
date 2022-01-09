package br.net.ricardotakemura.search.view;

import java.io.File;
import java.util.Set;

import br.net.ricardotakemura.search.view.impl.ConsoleSearchView;

public interface SearchView {
    void onCreatedIndex();
    void onFailedIndex(Exception e);
    void onLoadedIndex();
    void onSearchResult(Set<File> result);
    void start(String... args);

    static SearchView getInstance() {
        return new ConsoleSearchView();
    }
}
package br.net.ricardotakemura.search.view;

import java.io.File;
import java.util.Set;

import br.net.ricardotakemura.search.view.impl.ConsoleSearchViewImpl;

public interface SearchView {
    void start(String... args);
    void onSearchResult(Set<File> result);
    void onCreatedIndex();
    void onLoadedIndex();
    void onFailedIndex(Exception e);

    static SearchView getInstance() {
        return new ConsoleSearchViewImpl();
    }
}
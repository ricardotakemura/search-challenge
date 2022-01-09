package br.net.ricardotakemura.search;

import br.net.ricardotakemura.search.view.SearchView;

public final class AppMain {

    public static void main(String... args) {
        var searchView = SearchView.getInstance();
        searchView.start(args);
    }

}
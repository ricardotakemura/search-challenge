package br.net.ricardotakemura.search.view;

import java.io.File;
import java.util.Set;

import br.net.ricardotakemura.search.view.impl.ConsoleSearchViewImpl;

public interface SearchView {
    void onReceived(Set<File> files);
    void start(String... args);

    static SearchView getInstance() {
        return new ConsoleSearchViewImpl();
    }
}
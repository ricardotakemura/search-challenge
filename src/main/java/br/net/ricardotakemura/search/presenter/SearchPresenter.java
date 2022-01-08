package br.net.ricardotakemura.search.presenter;

import java.io.File;

public interface SearchPresenter {
    void loadIndexes();
    void search(String words);
    void createIndexes(File dataDir);
}
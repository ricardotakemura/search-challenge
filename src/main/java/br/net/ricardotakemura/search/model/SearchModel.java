package br.net.ricardotakemura.search.model;

import java.io.File;
import java.util.Set;

public interface SearchModel {

    void load() throws Exception;
    void createIndexes() throws Exception;
    Set<File> search(String[] words);
}

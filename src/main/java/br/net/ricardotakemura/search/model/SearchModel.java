package br.net.ricardotakemura.search.model;

import java.io.File;
import java.util.Set;

public interface SearchModel {

    void loadIndexes() throws Exception;
    void createIndexes(File dataDir) throws Exception;
    Set<File> search(String[] words);
}

package br.net.ricardotakemura.search.model;

import java.io.File;
import java.util.Set;

public interface SearchModel {
    void createIndexes(File dataDir) throws Exception;
    void loadIndexes() throws Exception;
    Set<File> search(String... words);
}

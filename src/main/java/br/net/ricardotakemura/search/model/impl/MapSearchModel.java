package br.net.ricardotakemura.search.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.util.Configuration;
import br.net.ricardotakemura.search.util.FileUtils;
import br.net.ricardotakemura.search.util.StringUtils;

public class MapSearchModel implements SearchModel {

    private HashMap<String, TreeSet<File>> data;
    private final File indexFile;

    public MapSearchModel() {
        var configuration = Configuration.getInstance();
        indexFile = new File(configuration.getProperty("application.data.index.file"));
    }

    public void loadIndexes() throws IOException, ClassNotFoundException {
        data = FileUtils.readFileData(indexFile);
    }

    public void createIndexes(File dataDir) throws IOException {
        data = new HashMap<>();
        if (dataDir.isDirectory() && dataDir.listFiles() != null) {
            for (var file: dataDir.listFiles()) {
                if (FileUtils.canRead(file)) {
                    createDataByFile(data, file);
                }
            }
        }
        FileUtils.createFileData(indexFile, data);
    }
    
    public Set<File> search(String... words) {
        var files = new TreeSet<File>();
        if (words == null) {
            return new TreeSet<>();
        }
        for (var word : words) {
            if (!StringUtils.isBlank(word)) {
                word = word.trim().toLowerCase();
                if (!data.containsKey(word)) {
                    return new TreeSet<>();
                }
                var items = data.get(word);
                files = files.isEmpty() ? new TreeSet<>(items) :
                        new TreeSet<>(items.stream().filter(files::contains).collect(Collectors.toSet()));
                if (files.isEmpty()) {
                    return files;
                }
            }
        }
        return files;
    }

    private void createDataByFile(HashMap<String, TreeSet<File>> data, File file) throws IOException {
        var str = FileUtils.readFully(file);
        for (var key: str.split("[\\W|_]")) {
            key = key.trim().toLowerCase();
            if (StringUtils.isNotBlank(key)) {
                if (data.containsKey(key)) {
                    data.get(key).add(file);
                } else {
                    var treeSet = new TreeSet<File>();
                    treeSet.add(file);
                    data.put(key, treeSet);
                }
            }
        }
    }
}

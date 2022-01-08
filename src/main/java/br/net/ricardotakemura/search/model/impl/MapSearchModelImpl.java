package br.net.ricardotakemura.search.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.util.FileUtils;

public class MapSearchModelImpl implements SearchModel {

    protected HashMap<String, TreeSet<File>> data;
    private static final File INDEX_FILE = new File("data.index");

    public void loadIndexes() throws IOException, ClassNotFoundException {
        data = FileUtils.readFileData(INDEX_FILE);
    }

    public void createIndexes(File dataDir) throws IOException {
        data = new HashMap<>();
        if (dataDir.isDirectory()) {
            for (var file: dataDir.listFiles()) {
                if (FileUtils.canRead(file)) {
                    createDataByFile(data, file);
                }
            }
        }
        FileUtils.createFileData(INDEX_FILE, data);
    }
    
    public Set<File> search(String[] words) {
        var files = new TreeSet<File>();
        for (var word: words) {
            word = word.toLowerCase();
            if (!data.containsKey(word)) {
                return new TreeSet<File>();
            }
            var items = data.get(word);
            files = files.isEmpty() ? new TreeSet<>(items) : 
                new TreeSet<>(items.stream().filter(files::contains).collect(Collectors.toSet()));
            if (files.isEmpty()) {
                return files;
            }
        }
        return files;
    }

    private void createDataByFile(HashMap<String, TreeSet<File>> data, File file) throws IOException {
        var str = FileUtils.readFully(file);
        for (var key: str.split(" ")) {
            key = key.toLowerCase();
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

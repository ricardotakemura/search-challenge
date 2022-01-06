package br.net.ricardotakemura.search.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.util.FileUtils;

public class TreeMapSearchModelImpl implements SearchModel {

    protected TreeMap<String, TreeSet<File>> data;
    private File dataFile = new File("data.index");

    public void load() throws IOException, ClassNotFoundException {
        data = FileUtils.readFileData(dataFile);
    }

    public void createIndexes() throws IOException {
        data = new TreeMap<>();
        var dir = new File("../data");
        if (dir.isDirectory()) {
            for (var file: dir.listFiles()) {
                if (FileUtils.canRead(file)) {
                    createDataByFile(data, file);
                }
            }
        }
        FileUtils.createFileData(dataFile, data);
    }
    
    public Set<File> search(String[] words) {
        var files = new TreeSet<File>();
        for (var word: words) {
            if (!data.containsKey(word)) {
                files.clear();
                return files;
            } else {
                var items = data.get(word);
                files = files.isEmpty() ? new TreeSet<>(items) : 
                    new TreeSet<>(items.stream().filter(files::contains).collect(Collectors.toSet()));
            }
        }
        return files;
    }

    private void createDataByFile(TreeMap<String, TreeSet<File>> data, File file) throws IOException {
        var str = FileUtils.readFully(file);
        for (var key: str.split(" ")) {
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

package br.net.ricardotakemura.search.model.impl;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.util.Configuration;
import br.net.ricardotakemura.search.util.FileUtils;
import br.net.ricardotakemura.search.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Classe de modelo do sistema que usa arquivo binário de objetos e a estrutura de hash (<i>java.util.HashMap</i>) como indexador
 */
public class MapSearchModel implements SearchModel {

    private final File indexFile;
    private HashMap<String, TreeSet<File>> data;

    /**
     * Construtor (<i>default</i>)
     */
    public MapSearchModel() {
        indexFile = new File(Configuration.getInstance().getProperty("application.data.index.file"));
    }

    /**
     * @see SearchModel#loadIndexes()
     */
    public void loadIndexes() throws IOException, ClassNotFoundException {
        data = FileUtils.readFileData(indexFile);
    }

    /**
     * @see SearchModel#createIndexes(File)
     */
    public void createIndexes(File dataDir) throws IOException {
        data = new HashMap<>();
        for (var file : FileUtils.getFilesByDirectory(dataDir)) {
            if (FileUtils.canRead(file)) {
                createDataByFile(data, file);
            }
        }
        FileUtils.createFileData(indexFile, data);
    }

    /**
     * @see SearchModel#search(String...)
     */
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
                        items.stream().filter(files::contains).collect(Collectors.toCollection(TreeSet::new));
                if (files.isEmpty()) {
                    return files;
                }
            }
        }
        return files;
    }

    private void createDataByFile(HashMap<String, TreeSet<File>> data, File file) throws IOException {
        var str = FileUtils.readFully(file);
        for (var key : str.split("[\\W|_]")) {
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

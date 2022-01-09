package br.net.ricardotakemura.search.model.impl;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.util.Configuration;
import br.net.ricardotakemura.search.util.FileUtils;
import br.net.ricardotakemura.search.util.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Classe de modelo do sistema que usa arquivo binário de objetos e a estrutura de lista (<i>java.util.ArrayList</i>) como indexador
 */
public class StreamSearchModel implements SearchModel {

    private final File indexFile;
    private ArrayList<FileContent> data;
    /**
     * Construtor (<i>default</i>)
     */
    public StreamSearchModel() {
        indexFile = new File(Configuration.getInstance().getProperty("application.data.index.file"));
    }

    /**
     * @see SearchModel#createIndexes(File)
     */
    @Override
    public void createIndexes(File dataDir) throws Exception {
        data = new ArrayList<>();
        for (var file : FileUtils.getFilesByDirectory(dataDir)) {
            if (FileUtils.canRead(file)) {
                data.add(new FileContent(file, FileUtils.readFully(file)));
            }
        }
        FileUtils.createFileData(indexFile, data);
    }

    /**
     * @see SearchModel#loadIndexes()
     */
    @Override
    public void loadIndexes() throws Exception {
        data = FileUtils.readFileData(indexFile);
    }

    /**
     * @see SearchModel#search(String...)
     */
    @Override
    public Set<File> search(String... words) {
        if (words == null) {
            return new TreeSet<>();
        }
        return data.stream().filter(it -> hasWords(it, words))
                .map(FileContent::getFile).collect(Collectors.toCollection(TreeSet::new));
    }

    private boolean hasWords(FileContent fileContent, String words[]) {
        var noWords = true;
        for (var word : words) {
            if (StringUtils.isNotBlank(word)) {
                noWords = false;
                var pattern = Pattern.compile(
                        MessageFormat.format("(^{0}\\W)|(\\W{0}$)|(\\W{0}\\W)|(^{0}$)", word),
                        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                if (!pattern.matcher(fileContent.getContent()).find()) {
                    return false;
                }
            }
        }
        return !noWords;
    }

    private static class FileContent implements Serializable {

        private File file;
        private String content;

        public FileContent(File file, String content) {
            setFile(file);
            setContent(content);
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

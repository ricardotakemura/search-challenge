package br.net.ricardotakemura.search.view.impl;

import java.io.File;
import java.util.Set;

import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.presenter.impl.SearchPresenterImpl;
import br.net.ricardotakemura.search.util.StringUtils;
import br.net.ricardotakemura.search.view.SearchView;

public class ConsoleSearchViewImpl implements SearchView {

    private String words;
    private final SearchPresenter searchPresenter;

    public ConsoleSearchViewImpl() {
        searchPresenter = new SearchPresenterImpl(this);
    }

    public void start(String... args) {
        if (args.length == 2) {
            if ("--createindex".equals(args[0]) && StringUtils.isNotBlank(args[1])) {
                System.out.println("Criando arquivo de indice...");
                searchPresenter.createIndexes(new File(args[1]));
            }
        } else if (args.length == 1 && StringUtils.isNotBlank(args[0])) {
            searchPresenter.loadIndexes();
            words = args[0];
            searchPresenter.search(words);
        }
    }
    
    public void onSearchResult(Set<File> result) {
        System.out.printf("Foram encontradas %d ocorrências pelo termo \"%s\".\n", result.size(), words);
        System.out.printf("Os arquivos que possuem \"%s\" são:\n", words);
        result.forEach(it -> System.out.println(it.getPath()));
    }
    
    public void onCreatedIndex() {
        System.out.println("Indice criado com sucesso.");
    }

    public void onLoadedIndex() {
        //DO NOTHING
    }
    
    public void onFailedIndex(Exception e) {
        System.err.printf("Falha ao criar/ler arquivo de indice: %s\n", e.getMessage());
        e.printStackTrace(System.err);
    }

}

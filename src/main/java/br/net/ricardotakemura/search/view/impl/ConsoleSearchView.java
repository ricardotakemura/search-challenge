package br.net.ricardotakemura.search.view.impl;

import java.io.File;
import java.util.Set;

import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.presenter.impl.SimpleSearchPresenter;
import br.net.ricardotakemura.search.util.StringUtils;
import br.net.ricardotakemura.search.view.SearchView;

/**
 * Classe de visão do sistema via linha de comando
 */
public class ConsoleSearchView implements SearchView {

    private String words;
    private final SearchPresenter searchPresenter;

    /**
     * Construtor (default)
     */
    public ConsoleSearchView() {
        searchPresenter = new SimpleSearchPresenter(this);
    }

    /**
     * @see SearchView#start(String...) 
     */
    @Override
    public void start(String... args) {
        if (args.length == 2 && "--createindex".equals(args[0]) && StringUtils.isNotBlank(args[1])) {
            System.out.println("Criando arquivo de indice...");
            searchPresenter.createIndexes(new File(args[1]));
        } else if (args.length == 1 && StringUtils.isNotBlank(args[0])) {
            words = args[0];
            searchPresenter.loadIndexes();
        } else {
            System.err.println("Comando inválido.");
            System.err.println("Para criar o arquivo de índice, faça:");
            System.err.println("\"java -jar search.jar --createindex <diretorio>\" -- sendo <diretorio> o diretório com arquivos a serem indexados");
            System.err.println("Para fazer a busca (após a indexação dos arquivos), faça:");
            System.err.println("\"java -jar search.jar \"palavras\" -- sendo \"palavras\" uma string com as palavras separadas por espaço");
        }
    }

    /**
     * @see SearchView#onSearchResult(Set)
     */
    @Override
    public void onSearchResult(Set<File> result) {
        if (result.isEmpty()) {
            System.out.printf("Não foram encontradas ocorrências pelo termo \"%s\".\n", words);
        } else {
            System.out.printf("Foram encontradas %d ocorrências pelo termo \"%s\".\n", result.size(), words);
            System.out.printf("Os arquivos que possuem \"%s\" são:\n", words);
            result.forEach(it -> System.out.println(it.getPath()));
        }
    }

    /**
     * @see SearchView#onCreatedIndex()
     */
    @Override
    public void onCreatedIndex() {
        System.out.println("Indice criado com sucesso.");
    }

    /**
     * @see SearchView#onLoadedIndex() 
     */
    @Override
    public void onLoadedIndex() {
        searchPresenter.search(words);
    }

    /**
     * @see SearchView#onFailedIndex(Exception)
     */
    @Override
    public void onFailedIndex(Exception e) {
        System.err.printf("Falha ao criar/ler arquivo de indice: %s\n", e.getMessage());
    }

}

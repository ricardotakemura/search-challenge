package br.net.ricardotakemura.search.presenter.impl;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.util.Log;
import br.net.ricardotakemura.search.view.SearchView;

import java.io.File;

/**
 * Classe de apresentação (implementação simples)
 */
public class SimpleSearchPresenter implements SearchPresenter {

    private final SearchModel searchModel;
    private final Log log;
    private SearchView searchView;

    /**
     * Construtor (<i>default</i>)
     */
    public SimpleSearchPresenter() {
        this(null);
    }

    /**
     * Construtor que recebe o objeto de visão (<i>SearchView</i>) para integração
     *
     * @param searchView Objeto de visão (<i>SearchView</i>)
     */
    public SimpleSearchPresenter(SearchView searchView) {
        this.searchView = searchView;
        this.searchModel = SearchModel.getInstance();
        this.log = Log.getLog(SimpleSearchPresenter.class);
    }

    /**
     * @see SearchPresenter#setView(SearchView)
     */
    @Override
    public void setView(SearchView view) {
        this.searchView = view;
    }

    /**
     * @see SearchPresenter#search(String)
     */
    @Override
    public void search(String words) {
        var start = System.currentTimeMillis();
        var result = searchModel.search(words.split(" "));
        log.debug("Tempo de execução da busca: " + (System.currentTimeMillis() - start) + "ms");
        if (searchView != null) {
            searchView.onSearchResult(result);
        }
    }

    /**
     * @see SearchPresenter#createIndexes(File)
     */
    @Override
    public void createIndexes(File dataDir) {
        try {
            var start = System.currentTimeMillis();
            searchModel.createIndexes(dataDir);
            log.debug("Tempo de execução ao criar arquivo de índices: " + (System.currentTimeMillis() - start) + "ms");
            if (searchView != null) {
                searchView.onCreatedIndex();
            }
        } catch (Exception e) {
            log.error("Erro ao executar o createIndexes", e);
            if (searchView != null) {
                searchView.onFailedIndex(e);
            }
        }
    }

    /**
     * @see SearchPresenter#loadIndexes()
     */
    @Override
    public void loadIndexes() {
        try {
            var start = System.currentTimeMillis();
            searchModel.loadIndexes();
            log.debug("Tempo de execução ao carregar arquivo de índices: " + (System.currentTimeMillis() - start) + "ms");
            if (searchView != null) {
                searchView.onLoadedIndex();
            }
        } catch (Exception e) {
            log.error("Erro ao executar o loadIndexes", e);
            if (searchView != null) {
                searchView.onFailedIndex(e);
            }
        }
    }
}

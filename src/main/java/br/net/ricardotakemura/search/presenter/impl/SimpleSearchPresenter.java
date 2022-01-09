package br.net.ricardotakemura.search.presenter.impl;

import java.io.File;

import br.net.ricardotakemura.search.model.SearchModel;
import br.net.ricardotakemura.search.model.impl.MapSearchModel;
import br.net.ricardotakemura.search.presenter.SearchPresenter;
import br.net.ricardotakemura.search.util.Log;
import br.net.ricardotakemura.search.view.SearchView;

public class SimpleSearchPresenter implements SearchPresenter {

    private SearchView searchView;
    private final SearchModel searchModel;
    private final Log log;

    public SimpleSearchPresenter(SearchView searchView) {
        this.searchView = searchView;
        this.searchModel = new MapSearchModel();
        this.log = Log.getLog(SimpleSearchPresenter.class);
    }

    public SimpleSearchPresenter() {
        this(null);
    }

    @Override
    public void setView(SearchView view) {
        this.searchView = view;
    }

    @Override
    public void search(String words) {
        var start = System.currentTimeMillis();
        var result = searchModel.search(words.split(" "));
        log.debug("Tempo de execução da busca: " + (System.currentTimeMillis() - start) + "ms");
        if (searchView != null) {
            searchView.onSearchResult(result);
        }
    }

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

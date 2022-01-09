package br.net.ricardotakemura.search.presenter;

import br.net.ricardotakemura.search.view.SearchView;

import java.io.File;

/**
 * <i>Interface</i> de apresentação do sistema (integração entre visão e modelo)
 */
public interface SearchPresenter {

    /**
     * Cria o arquivo de índices
     *
     * @param dataDir Diretório dos arquivos a serem indexados
     */
    void createIndexes(File dataDir);

    /**
     * Carrega o arquivo de índices
     */
    void loadIndexes();

    /**
     * Faz a busca pelas palavras passadas por parâmetro
     *
     * @param words Palavras a serem buscadas (separadas por espaço)
     */
    void search(String words);

    /**
     * Integra este objeto ao objeto de visão (<i>SearchView</i>)
     *
     * @param view Objeto de visão (<i>SearchView</i>)
     */
    void setView(SearchView view);
}
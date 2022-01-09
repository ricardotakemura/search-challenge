package br.net.ricardotakemura.search.view;

import java.io.File;
import java.util.Set;

import br.net.ricardotakemura.search.view.impl.ConsoleSearchView;

/**
 * <i>Interface</i> de visão do sistema (como é exibido)
 */
public interface SearchView {

    /**
     * Método de delegação executado após a criação do arquivo de índices
     */
    void onCreatedIndex();

    /**
     * Método de delegação executado após alguma falha ao criar ou ler o arquivo de índices
     * @param e Exceção ocorrida na execução
     */
    void onFailedIndex(Exception e);

    /**
     * Método de delegação executado após a leitura do arquivo de índices
     */
    void onLoadedIndex();

    /**
     * Método de delegação executado após a busca
     * @param result Resultado da busca
     */
    void onSearchResult(Set<File> result);

    /**
     * Método que inicia a operação desta classe (View)
     * @param args Argumentos passados por paramêtros
     */
    void start(String... args);

    /**
     * Obtém uma instância de SearchView (configurável)
     * @return Instância de SearchView
     */
    static SearchView getInstance() {
        return new ConsoleSearchView();
    }
}
package br.net.ricardotakemura.search.view;

import br.net.ricardotakemura.search.util.Configuration;
import br.net.ricardotakemura.search.view.impl.ConsoleSearchView;

import java.io.File;
import java.util.Set;

/**
 * <i>Interface</i> de visão do sistema (como é exibido)
 */
public interface SearchView {

    /**
     * Obtém uma instância de <i>SearchView</i> (configurável)
     *
     * @return Instância de <i>SearchView</i>
     */
    static SearchView getInstance() {
        try {
            var viewClassName = Configuration.getInstance().getProperty("application.view.class");
            return (SearchView) Class.forName(viewClassName).getConstructor().newInstance();
        } catch (Exception e) {
            return new ConsoleSearchView();
        }
    }

    /**
     * Método de delegação executado após a criação do arquivo de índices
     */
    void onCreatedIndex();

    /**
     * Método de delegação executado após alguma falha ao criar ou ler o arquivo de índices
     *
     * @param e Exceção ocorrida na execução
     */
    void onFailedIndex(Exception e);

    /**
     * Método de delegação executado após a leitura do arquivo de índices
     */
    void onLoadedIndex();

    /**
     * Método de delegação executado após a busca
     *
     * @param result Resultado da busca
     */
    void onSearchResult(Set<File> result);

    /**
     * Método que inicia a operação desta classe (<i>View</i>)
     *
     * @param args Argumentos passados por paramêtros
     */
    void start(String... args);
}